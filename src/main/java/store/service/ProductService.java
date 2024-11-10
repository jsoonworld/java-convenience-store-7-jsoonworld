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

            int remainingQuantity = requestedQuantity;

            if (promotionalProduct != null) {
                Optional<Promotion> validPromotion = promotionService.getValidPromotionForProduct(promotionalProduct.getPromotionName());
                if (validPromotion.isPresent()) {
                    Promotion promotion = validPromotion.get();
                    int buyQuantity = promotion.getBuyQuantity();
                    int freeQuantity = promotion.getFreeQuantity();

                    // 프로모션 조건 확인 및 무료 증정 수량 계산
                    int freeItems = (requestedQuantity / buyQuantity) * freeQuantity;
                    int totalRequiredQuantity = requestedQuantity + freeItems;  // 요청 수량 + 무료 증정 수량

                    int promoStock = promotionalProduct.getQuantityValue();

                    // 프로모션 재고에서 우선 차감
                    if (promoStock >= totalRequiredQuantity) {
                        promotionalProduct = promotionalProduct.decreaseQuantity(totalRequiredQuantity);
                        System.out.println("[INFO] 프로모션 적용: 요청 수량 " + requestedQuantity + "개와 무료 증정 수량 " + freeItems + "개 포함하여 총 " + totalRequiredQuantity + "개 차감.");
                        productsToSave.add(promotionalProduct);
                        remainingQuantity = 0;
                    } else {
                        // 프로모션 재고 부족 시 가능한 만큼만 차감하고 남은 수량은 일반 재고에서 차감
                        promotionalProduct = promotionalProduct.decreaseQuantity(promoStock);
                        productsToSave.add(promotionalProduct);
                        remainingQuantity -= promoStock;
                        System.out.println("[WARNING] 프로모션 재고 부족으로 일부 수량만 프로모션 혜택 적용, 남은 수량은 정가로 결제됩니다: " + remainingQuantity + "개");
                    }
                } else {
                    System.out.println("프로모션이 종료된 상품입니다: " + request.getProductName());
                }
            }

            // 남은 수량이 있을 경우 일반 재고에서 차감
            if (remainingQuantity > 0 && regularProduct != null) {
                if (regularProduct.getQuantityValue() >= remainingQuantity) {
                    regularProduct = regularProduct.decreaseQuantity(remainingQuantity);
                    System.out.println("[INFO] 일반 재고에서 차감: " + regularProduct.getName() + ", 차감된 수량: " + remainingQuantity);
                    productsToSave.add(regularProduct);
                } else {
                    System.out.println("[ERROR] 일반 재고가 부족하여 요청 수량을 충족할 수 없습니다: " + request.getProductName());
                    continue;
                }
            } else if (remainingQuantity > 0) {
                System.out.println("[ERROR] 재고가 부족합니다: " + request.getProductName());
                continue;
            }
        }
        // 모든 상품 업데이트
        productsToSave.forEach(productRepository::save);
    }
}

