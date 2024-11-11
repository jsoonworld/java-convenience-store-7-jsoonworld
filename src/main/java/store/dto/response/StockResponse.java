package store.dto.response;

import store.domain.product.Product;

public class StockResponse {
    private final int remainingQuantity;
    private final int discountAmount;
    private final Product updatedProduct;

    private StockResponse(int remainingQuantity, int discountAmount, Product updatedProduct) {
        this.remainingQuantity = remainingQuantity;
        this.discountAmount = discountAmount;
        this.updatedProduct = updatedProduct;
    }

    public static StockResponse of(int remainingQuantity, int discountAmount, Product updatedProduct) {
        return new StockResponse(remainingQuantity, discountAmount, updatedProduct);
    }

    public static StockResponse emptyResponse() {
        return new StockResponse(0, 0, null);
    }

    public int getRemainingQuantity() {
        return remainingQuantity;
    }

    public int getDiscountAmount() {
        return discountAmount;
    }

    public Product getUpdatedProduct() {
        return updatedProduct;
    }
}
