package store.dto.response;

import store.domain.product.Product;

import java.util.List;

public class PurchaseProcessingResult {
    private final int totalPrice;
    private final int promotionalDiscount;
    private final int finalQuantity;
    private final String purchaseDetails;
    private final String promotionalDetails;
    private final List<Product> productsToSave;

    private PurchaseProcessingResult(int totalPrice, int promotionalDiscount, int finalQuantity, String purchaseDetails, String promotionalDetails, List<Product> productsToSave) {
        this.totalPrice = totalPrice;
        this.promotionalDiscount = promotionalDiscount;
        this.finalQuantity = finalQuantity;
        this.purchaseDetails = purchaseDetails;
        this.promotionalDetails = promotionalDetails;
        this.productsToSave = productsToSave;
    }

    public static PurchaseProcessingResult of(int totalPrice, int promotionalDiscount, int finalQuantity, String purchaseDetails, String promotionalDetails, List<Product> productsToSave) {
        return new PurchaseProcessingResult(totalPrice, promotionalDiscount, finalQuantity, purchaseDetails, promotionalDetails, productsToSave);
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public int getPromotionalDiscount() {
        return promotionalDiscount;
    }

    public int getFinalQuantity() {
        return finalQuantity;
    }

    public String getPurchaseDetails() {
        return purchaseDetails;
    }

    public String getPromotionalDetails() {
        return promotionalDetails;
    }

    public List<Product> getProductsToSave() {
        return productsToSave;
    }
}

