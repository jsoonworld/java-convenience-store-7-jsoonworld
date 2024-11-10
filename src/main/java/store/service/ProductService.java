package store.service;

import store.domain.Product;
import store.domain.Promotion;
import store.dto.PurchaseProductRequest;
import store.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductService {
    private final ProductRepository productRepository;
    private final PromotionService promotionService;

    public ProductService(ProductRepository productRepository, PromotionService promotionService) {
        this.productRepository = productRepository;
        this.promotionService = promotionService;
    }

    public List<Product> getAllProducts() {
        return productRepository.getAllProducts();
    }

    public Product getProductByName(String productName) {
        return productRepository.findProductByName(productName);
    }

    public void updateProduct(Product updatedProduct) {
        productRepository.save(updatedProduct);
    }

    public Product getProductByNameAndPromotion(String productName, String promotionName) {
        return productRepository.findByNameAndPromotion(productName, promotionName);
    }

    public void processPurchase(List<PurchaseProductRequest> purchaseProductRequests) {
        List<Product> productsToSave = new ArrayList<>();

        for (PurchaseProductRequest request : purchaseProductRequests) {
            Product product = getProductByName(request.getProductName());
            if (product == null) {
                System.out.println("상품을 찾을 수 없습니다: " + request.getProductName());
                continue;
            }

            int requestedQuantity = request.getQuantity();
            System.out.println("[INFO] 요청된 상품: " + product.getName() + ", 요청 수량: " + requestedQuantity);


            Product promotionalProduct = productRepository.findByNameAndPromotion(product.getName(), product.getPromotionName());
            Product regularProduct = productRepository.findByNameAndPromotion(product.getName(), "null");

            // 프로모션 무료 증정 개수 추출
            List<Promotion> promotions = promotionService.findPromotionsByName(product.getPromotionName());
            int freeQuantity = promotions.get(0).getFreeQuantity();


            Product productToUpdate = promotionalProduct;
            int remainingQuantity = requestedQuantity;

            if (promotionalProduct != null) {
                Optional<Promotion> validPromotion = promotionService.getValidPromotionForProduct(promotionalProduct.getPromotionName());
                if (validPromotion.isPresent()) {
                    int promoStock = promotionalProduct.getQuantityValue();

                    if (promoStock >= remainingQuantity+freeQuantity) {
                        productToUpdate = promotionalProduct.decreaseQuantity(remainingQuantity+freeQuantity);
                        remainingQuantity = 0;
                    } else {
                        productToUpdate = promotionalProduct.decreaseQuantity(promoStock);
                        remainingQuantity -= promoStock;
                    }
                    productsToSave.add(productToUpdate);
                } else {
                    System.out.println("프로모션이 종료된 상품입니다: " + request.getProductName());
                }
            }

            if (remainingQuantity > 0 && regularProduct != null) {
                if (regularProduct.getQuantityValue() >= remainingQuantity) {
                    Product updatedRegularProduct = regularProduct.decreaseQuantity(remainingQuantity);
                    productsToSave.add(updatedRegularProduct);
                } else {
                    System.out.println("일반 재고가 부족합니다: " + request.getProductName());
                    continue;
                }
            } else if (remainingQuantity > 0) {
                System.out.println("재고가 부족합니다: " + request.getProductName());
                continue;
            }
        }

        for (Product product : productsToSave) {
            productRepository.save(product);
        }
    }
}