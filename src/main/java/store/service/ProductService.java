package store.service;

import camp.nextstep.edu.missionutils.Console;
import store.domain.Product;
import store.domain.Promotion;
import store.domain.vo.Price;
import store.domain.vo.ProductName;
import store.domain.vo.PromotionName;
import store.domain.vo.Quantity;
import store.dto.PurchaseProductRequest;
import store.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductService {
    private final ProductRepository productRepository;
    private final PromotionService promotionService;
    private final ReceiptPrinter receiptPrinter;  // ReceiptPrinter 객체 추가

    public ProductService(ProductRepository productRepository, PromotionService promotionService, ReceiptPrinter receiptPrinter) {
        this.productRepository = productRepository;
        this.promotionService = promotionService;
        this.receiptPrinter = receiptPrinter;  // 의존성 주입
    }

    // 전체 상품을 조회하여 반환하는 메서드
    public List<Product> getAllProducts() {
        return productRepository.getAllProducts();
    }

    // 이름을 기준으로 상품을 조회하여 반환하는 메서드
    public Product getProductByName(String productName) {
        return productRepository.findProductByName(productName);
    }

    // 구매 처리 메서드
    public void processPurchase(List<PurchaseProductRequest> purchaseProductRequests) {
        List<Product> productsToSave = new ArrayList<>();
        int totalPrice = 0;  // 총 가격
        int totalPromotionalDiscount = 0;  // 총 행사 할인
        int totalFinalQuantity = 0;  // 총 구매 수량

        StringBuilder purchaseDetails = new StringBuilder();  // 구매 내역
        StringBuilder promotionalDetails = new StringBuilder();  // 프로모션 내역

        boolean isMembershipDiscountApplied = false; // 멤버십 할인 여부

        // 각 요청에 대해 상품을 처리
        for (PurchaseProductRequest request : purchaseProductRequests) {
            Product product = getProductByName(request.getProductName());
            if (product == null) {
                handleProductNotFound(request.getProductName());
                continue;
            }

            // 총 구매 수량 계산
            int requestedQuantity = request.getQuantity();
            int productPrice = product.getPriceValue();

            if (requestedQuantity > product.getQuantityValue()) {
                System.out.println("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
                continue;  // 해당 상품을 건너뛰고 다음 상품으로 넘어감
            }

            // 프로모션 처리
            int remainingQuantity = handlePromotion(productsToSave, product, requestedQuantity, productPrice, promotionalDetails);

            // 일반 재고 처리
            totalPromotionalDiscount += handleRegularStock(productsToSave, product, requestedQuantity, remainingQuantity, productPrice);

            if(remainingQuantity == 0){
                totalFinalQuantity += requestedQuantity;
            } else {
                totalFinalQuantity = remainingQuantity;
            }

            // 구매 내역에 추가 (사이다 - 수량 - 금액)
            purchaseDetails.append(formatPurchaseDetails(product, totalFinalQuantity, productPrice));
            totalPrice += productPrice * requestedQuantity;
        }

        // 멤버십 할인 여부 처리
        System.out.println("멤버십 할인을 받으시겠습니까? (Y/N)");
        String membershipInput = Console.readLine().trim().toUpperCase();
        if (membershipInput.equals("Y")) {
            isMembershipDiscountApplied = true; // 멤버십 할인 적용
        }


        // 영수증 출력
        receiptPrinter.printReceipt(purchaseDetails, promotionalDetails, totalFinalQuantity, totalPrice, totalPromotionalDiscount, isMembershipDiscountApplied);
        // 업데이트된 상품 저장
        saveUpdatedProducts(productsToSave);
    }

    // 상품을 찾을 수 없을 때 처리하는 메서드
    private void handleProductNotFound(String productName) {
        System.out.println("상품을 찾을 수 없습니다: " + productName);
    }

    // 프로모션이 있는 상품에 대해 처리하는 메서드
    private int handlePromotion(List<Product> productsToSave, Product product, int requestedQuantity, int productPrice, StringBuilder promotionalDetails) {
        int finalQuantity = requestedQuantity;  // 초기 요청된 수량으로 초기화
        Product promotionalProduct = productRepository.findByNameAndPromotion(product.getName(), product.getPromotionName());

        if (promotionalProduct != null) {
            // 유효한 프로모션이 있을 경우
            Optional<Promotion> validPromotion = promotionService.getValidPromotionForProduct(promotionalProduct.getPromotionName());
            if (validPromotion.isPresent()) {
                // applyPromotion에서 최종 수량을 계산하여 반영
                finalQuantity = applyPromotion(productsToSave, promotionalProduct, requestedQuantity, productPrice, validPromotion.get(), promotionalDetails);
            }
        }

        return finalQuantity;
    }

    // applyPromotion 메서드
    private int applyPromotion(List<Product> productsToSave, Product promotionalProduct, int requestedQuantity, int productPrice, Promotion promotion, StringBuilder promotionalDetails) {
        int finalQuantity = requestedQuantity; // 초기 요청된 수량으로 초기화
        int buyQuantity = promotion.getBuyQuantity();
        int freeQuantity = promotion.getFreeQuantity();

        int freeItems = (finalQuantity / buyQuantity) * freeQuantity; // 무료 제공되는 수량
        int totalRequiredQuantity = finalQuantity + freeItems; // 프로모션 만족 총 수량
        int promoStock = promotionalProduct.getQuantityValue(); // 프로모션 재고

        int discountAmount = freeItems * productPrice; // 할인 금액

        if (requestedQuantity < buyQuantity) {
            int unmetPromotionQuantity = buyQuantity - requestedQuantity;
            System.out.printf("고객님은 프로모션 조건을 충족하지 못하셨습니다. (%d+%d 프로모션의 경우, %d개 이상 구매 시 증정이 가능합니다.)%n", buyQuantity, freeQuantity, buyQuantity);
            System.out.println("추가로 필요한 수량: " + unmetPromotionQuantity + "개");
            System.out.println("추가 구매를 진행하시겠습니까? (Y/N)");

            String userInput = Console.readLine().trim().toUpperCase();
            if (userInput.equals("Y")) {
                finalQuantity += unmetPromotionQuantity; // 추가 구매 반영

                // 추가 구매 상품
                Product additionalProduct = Product.of(
                        ProductName.from(promotionalProduct.getName()),
                        Price.from(promotionalProduct.getPriceValue()),
                        Quantity.from(unmetPromotionQuantity),
                        PromotionName.from("null")
                );
                productsToSave.add(additionalProduct);
//                promotionalDetails.append(String.format("%-10s\t%d (추가 구매)\n", additionalProduct.getName(), unmetPromotionQuantity));

                // 조건 충족 후, 증정 상품 추가
                freeItems = (finalQuantity / buyQuantity) * freeQuantity;
                Product freeProduct = Product.of(
                        ProductName.from(promotionalProduct.getName()),
                        Price.from(promotionalProduct.getPriceValue()),
                        Quantity.from(freeItems),
                        PromotionName.from("null")
                );
                productsToSave.add(freeProduct);
                promotionalDetails.append(String.format("%-10s\t%d\n", freeProduct.getName(), freeItems));

                // 할인 금액 업데이트
                discountAmount = freeItems * productPrice;

//                promotionalDetails.append(String.format("행사할인: %,d원\n", discountAmount));
            } else {
                processRemainingStock(productsToSave, promotionalProduct, totalRequiredQuantity, promoStock, promotionalDetails, discountAmount, freeItems);
                finalQuantity = 0; // 남은 수량 없음
            }
        } else {
            processRemainingStock(productsToSave, promotionalProduct, totalRequiredQuantity, promoStock, promotionalDetails, discountAmount, freeItems);
            finalQuantity = 0; // 남은 수량 없음
        }

        return finalQuantity;
    }


    // 남은 재고를 처리하는 메서드
    private void processRemainingStock(List<Product> productsToSave, Product promotionalProduct, int totalRequiredQuantity, int promoStock, StringBuilder promotionalDetails, int discountAmount, int freeItems) {
        if (promoStock >= totalRequiredQuantity) {
            promotionalProduct = promotionalProduct.decreaseQuantity(totalRequiredQuantity);
            productsToSave.add(promotionalProduct);
        } else {
            promotionalProduct = promotionalProduct.decreaseQuantity(promoStock);
            productsToSave.add(promotionalProduct);
        }
        promotionalDetails.append(String.format("%-10s\t%d\n", promotionalProduct.getName(), freeItems));
//        promotionalDetails.append(String.format("할인 금액: %,d원\n", discountAmount)); // 한 번만 출력
    }



    // 일반 재고 처리하는 메서드
    private int handleRegularStock(List<Product> productsToSave, Product product, int requestedQuantity, int remainingQuantity, int productPrice) {
        int totalPromotionalDiscount = 0;
        // 프로모션을 다 사용한 후 남은 수량에 대해 일반 재고에서 차감
        if (remainingQuantity > 0) {
            Product regularProduct = productRepository.findByNameAndPromotion(product.getName(), "null");
            if (regularProduct != null && regularProduct.getQuantityValue() >= remainingQuantity) {
                regularProduct = regularProduct.decreaseQuantity(remainingQuantity);
                productsToSave.add(regularProduct);
                // 할인 적용된 가격 추가
                totalPromotionalDiscount = productPrice * (requestedQuantity - remainingQuantity);
            }
        }
        return totalPromotionalDiscount;
    }

    // 구매 내역을 포맷에 맞게 변환하는 메서드
    private String formatPurchaseDetails(Product product, int requestedQuantity, int productPrice) {
        return String.format("%-10s\t%d\t%,d\n", product.getName(), requestedQuantity, productPrice * requestedQuantity);
    }

    // 업데이트된 상품을 저장하는 메서드
    private void saveUpdatedProducts(List<Product> productsToSave) {
        productsToSave.forEach(productRepository::save);
    }
}
