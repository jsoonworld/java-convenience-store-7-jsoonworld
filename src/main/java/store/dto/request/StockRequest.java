package store.dto.request;

public class StockRequest {
    private final String productName;
    private final int remainingQuantity;
    private final int productPrice;
    private final int requestedQuantity;

    private StockRequest(String productName, int remainingQuantity, int productPrice, int requestedQuantity) {
        this.productName = productName;
        this.remainingQuantity = remainingQuantity;
        this.productPrice = productPrice;
        this.requestedQuantity = requestedQuantity;
    }

    public static StockRequest of(String productName, int remainingQuantity, int productPrice, int requestedQuantity) {
        return new StockRequest(productName, remainingQuantity, productPrice, requestedQuantity);
    }

    public String getProductName() {
        return productName;
    }

    public int getRemainingQuantity() {
        return remainingQuantity;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public int getRequestedQuantity() {
        return requestedQuantity;
    }
}
