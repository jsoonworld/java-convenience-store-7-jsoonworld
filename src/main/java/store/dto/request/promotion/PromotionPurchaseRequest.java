package store.dto.request.promotion;

public class PromotionPurchaseRequest {
    private final String productName;
    private final int requestedQuantity;

    private PromotionPurchaseRequest(String productName, int requestedQuantity) {
        this.productName = productName;
        this.requestedQuantity = requestedQuantity;
    }

    public static PromotionPurchaseRequest of(String productName, int requestedQuantity) {
        return new PromotionPurchaseRequest(productName, requestedQuantity);
    }

    public String getProductName() {
        return productName;
    }

    public int getRequestedQuantity() {
        return requestedQuantity;
    }
}
