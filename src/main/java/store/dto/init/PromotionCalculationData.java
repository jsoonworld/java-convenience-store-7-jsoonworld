package store.dto.init;

import store.domain.Product;
import store.dto.request.PromotionRequest;

import java.util.ArrayList;
import java.util.List;

public class PromotionCalculationData {

    private final int buyQuantity;
    private final int freeQuantity;
    private final int finalQuantity;
    private final int freeItems;
    private final int totalRequiredQuantity;
    private final int promoStock;
    private final int discountAmount;
    private final List<Product> productsToSave;
    private final StringBuilder promotionalDetails;

    private PromotionCalculationData(int buyQuantity, int freeQuantity, int finalQuantity, int freeItems, int totalRequiredQuantity, int promoStock, int discountAmount) {
        this.buyQuantity = buyQuantity;
        this.freeQuantity = freeQuantity;
        this.finalQuantity = finalQuantity;
        this.freeItems = freeItems;
        this.totalRequiredQuantity = totalRequiredQuantity;
        this.promoStock = promoStock;
        this.discountAmount = discountAmount;
        this.productsToSave = new ArrayList<>();
        this.promotionalDetails = new StringBuilder();
    }

    public static PromotionCalculationData of(PromotionRequest request) {
        int buyQuantity = request.getBuyQuantity();
        int freeQuantity = request.getFreeQuantity();
        int finalQuantity = request.getRequestedQuantity();
        int freeItems = calculateFreeItems(finalQuantity, buyQuantity, freeQuantity);
        int totalRequiredQuantity = finalQuantity + freeItems;
        int promoStock = request.getStock();
        int discountAmount = freeItems * request.getProductPrice();

        return new PromotionCalculationData(buyQuantity, freeQuantity, finalQuantity, freeItems, totalRequiredQuantity, promoStock, discountAmount);
    }


    public PromotionCalculationData withUpdatedQuantities(int newFinalQuantity, int newFreeItems) {
        return new PromotionCalculationData(
                this.buyQuantity,
                this.freeQuantity,
                newFinalQuantity,
                newFreeItems,
                newFinalQuantity + newFreeItems,
                this.promoStock,
                newFreeItems * this.discountAmount
        );
    }

    public int getBuyQuantity() { return buyQuantity; }
    public int getFreeQuantity() { return freeQuantity; }
    public int getFinalQuantity() { return finalQuantity; }
    public int getFreeItems() { return freeItems; }
    public int getTotalRequiredQuantity() { return totalRequiredQuantity; }
    public int getPromoStock() { return promoStock; }
    public int getDiscountAmount() { return discountAmount; }
    public List<Product> getProductsToSave() { return productsToSave; }
    public StringBuilder getPromotionalDetails() { return promotionalDetails; }

    private static int calculateFreeItems(int finalQuantity, int buyQuantity, int freeQuantity) {
        if (buyQuantity == 0) {
            return 0;
        }
        return (finalQuantity / (buyQuantity + freeQuantity));
    }
}
