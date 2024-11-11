package store.dto.response.promotion;

import store.domain.product.Product;

import java.util.List;

public class PromotionResponse {
    private final int finalQuantity;
    private final int discountAmount;
    private final List<Product> productsToSave;
    private final StringBuilder promotionalDetails;

    private PromotionResponse(int finalQuantity, int discountAmount, List<Product> productsToSave, StringBuilder promotionalDetails) {
        this.finalQuantity = finalQuantity;
        this.discountAmount = discountAmount;
        this.productsToSave = productsToSave;
        this.promotionalDetails = promotionalDetails;
    }

    public static PromotionResponse of(int finalQuantity, int discountAmount, List<Product> productsToSave, StringBuilder promotionalDetails) {
        return new PromotionResponse(finalQuantity, discountAmount, productsToSave, promotionalDetails);
    }

    public static PromotionResponse emptyResponse(int requestedQuantity) {
        return new PromotionResponse(requestedQuantity, 0, List.of(), new StringBuilder());
    }

    public int getFinalQuantity() { return finalQuantity; }
    public int getDiscountAmount() { return discountAmount; }
    public List<Product> getProductsToSave() { return productsToSave; }
    public StringBuilder getPromotionalDetails() { return promotionalDetails; }

}
