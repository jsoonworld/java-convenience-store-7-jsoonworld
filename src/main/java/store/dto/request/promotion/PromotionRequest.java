package store.dto.request.promotion;

public class PromotionRequest {
    private final String productName;
    private final int requestedQuantity;
    private final int productPrice;
    private final int buyQuantity;
    private final int freeQuantity;
    private final int stock;
    private final String promotionName;

    private PromotionRequest(String productName, int requestedQuantity, int productPrice, int buyQuantity, int freeQuantity, int stock, String promotionName) {
        this.productName = productName;
        this.requestedQuantity = requestedQuantity;
        this.productPrice = productPrice;
        this.buyQuantity = buyQuantity;
        this.freeQuantity = freeQuantity;
        this.stock = stock;
        this.promotionName = promotionName;
    }

    public static PromotionRequest of(String productName, int requestedQuantity, int productPrice, int buyQuantity, int freeQuantity, int stock, String promotionName) {
        return new PromotionRequest(productName, requestedQuantity, productPrice, buyQuantity, freeQuantity, stock, promotionName);
    }

    public String getProductName() { return productName; }
    public int getRequestedQuantity() { return requestedQuantity; }
    public int getProductPrice() { return productPrice; }
    public int getBuyQuantity() { return buyQuantity; }
    public int getFreeQuantity() { return freeQuantity; }
    public int getStock() { return stock; }
    public String getPromotionName() { return promotionName; }
}
