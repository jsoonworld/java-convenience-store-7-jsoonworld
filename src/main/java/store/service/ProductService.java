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
        List<Product> allProducts = productRepository.getAllProducts();
        return allProducts;
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
        int totalPrice = 0;
        int totalPromotionalDiscount = 0;
        int totalFinalQuantity = 0;
        int totalPaidQuantity = 0;

        StringBuilder purchaseDetails = new StringBuilder();
        StringBuilder promotionalDetails = new StringBuilder();

        for (PurchaseProductRequest request : purchaseProductRequests) {
            Product product = getProductByName(request.getProductName());
            if (product == null) {
                System.out.println("상품을 찾을 수 없습니다: " + request.getProductName());
                continue;
            }

            int requestedQuantity = request.getQuantity();
            int productPrice = product.getPriceValue();
            totalPaidQuantity += requestedQuantity;

            Product promotionalProduct = productRepository.findByNameAndPromotion(product.getName(), product.getPromotionName());
            Product regularProduct = productRepository.findByNameAndPromotion(product.getName(), "null");

            int remainingQuantity = requestedQuantity;
            int freeItems = 0;

            if (promotionalProduct != null) {
                Optional<Promotion> validPromotion = promotionService.getValidPromotionForProduct(promotionalProduct.getPromotionName());
                if (validPromotion.isPresent()) {
                    Promotion promotion = validPromotion.get();
                    int buyQuantity = promotion.getBuyQuantity();
                    int freeQuantity = promotion.getFreeQuantity();

                    freeItems = (requestedQuantity / buyQuantity) * freeQuantity;
                    int totalRequiredQuantity = requestedQuantity + freeItems;
                    int promoStock = promotionalProduct.getQuantityValue();

                    if (promoStock >= totalRequiredQuantity) {
                        promotionalProduct = promotionalProduct.decreaseQuantity(totalRequiredQuantity);
                        productsToSave.add(promotionalProduct);
                        remainingQuantity = 0;
                        totalPrice += productPrice * requestedQuantity;
                        totalPromotionalDiscount += productPrice * freeItems;
                    } else {
                        promotionalProduct = promotionalProduct.decreaseQuantity(promoStock);
                        productsToSave.add(promotionalProduct);
                        remainingQuantity -= promoStock;
                        totalPrice += productPrice * (requestedQuantity - freeItems);
                        totalPromotionalDiscount += productPrice * freeItems;
                    }
                    promotionalDetails.append(String.format("%-10s\t%d\n", product.getName(), freeItems));
                }
            }

            if (remainingQuantity > 0 && regularProduct != null) {
                if (regularProduct.getQuantityValue() >= remainingQuantity) {
                    regularProduct = regularProduct.decreaseQuantity(remainingQuantity);
                    productsToSave.add(regularProduct);
                    totalPrice += productPrice * remainingQuantity;
                }
            }

            purchaseDetails.append(String.format("%-10s\t%d\t%,d\n", product.getName(), requestedQuantity, productPrice * requestedQuantity));
            totalFinalQuantity += requestedQuantity;
        }

        // 멤버십 할인 적용
        int membershipDiscount = (int) Math.min(totalPrice * 0.3, 8000);
        int finalPrice = totalPrice - membershipDiscount;

        // 영수증 출력
        System.out.println("\n===========W 편의점=============");
        System.out.println("상품명\t\t수량\t금액");
        System.out.print(purchaseDetails.toString());
        System.out.println("===========증    정=============");
        System.out.print(promotionalDetails.toString());
        System.out.println("===============================");
        System.out.printf("총구매액\t\t%d\t%,d원\n", totalFinalQuantity, totalPrice);
        System.out.printf("행사할인\t\t\t-%,d원\n", totalPromotionalDiscount);
        System.out.printf("멤버십할인\t\t\t-%,d원\n", membershipDiscount);
        System.out.printf("내실돈\t\t\t%,d원\n", finalPrice);

        productsToSave.forEach(productRepository::save);
    }
}

