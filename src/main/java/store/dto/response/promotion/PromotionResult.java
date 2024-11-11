package store.dto.response.promotion;

import store.domain.product.Product;

import java.util.List;

public class PromotionResult {
    private final int remainingQuantity;
    private final int totalPromotionalDiscount;
    private final List<Product> productsToSave;
    private final StringBuilder promotionalDetails;

    private PromotionResult(int remainingQuantity, int totalPromotionalDiscount, List<Product> productsToSave, StringBuilder promotionalDetails) {
        this.remainingQuantity = remainingQuantity;
        this.totalPromotionalDiscount = totalPromotionalDiscount;
        this.productsToSave = productsToSave;
        this.promotionalDetails = promotionalDetails;
    }

    public static PromotionResult of(int remainingQuantity, int totalPromotionalDiscount, List<Product> productsToSave, StringBuilder promotionalDetails) {
        return new PromotionResult(remainingQuantity, totalPromotionalDiscount, productsToSave, promotionalDetails);
    }

    public int getRemainingQuantity() {
        return remainingQuantity;
    }

    public int getTotalPromotionalDiscount() {
        return totalPromotionalDiscount;
    }

    public List<Product> getProductsToSave() {
        return productsToSave;
    }

    public StringBuilder getPromotionalDetails() {
        return promotionalDetails;
    }
}
