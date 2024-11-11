package store.dto.init;

import store.domain.Product;

import java.util.ArrayList;
import java.util.List;

public class PurchaseProcessData {
    private static final int INITIAL_TOTAL_PRICE = 0;
    private static final int INITIAL_PROMOTIONAL_DISCOUNT = 0;
    private static final int INITIAL_FINAL_QUANTITY = 0;

    public final List<Product> productsToSave;
    public final StringBuilder purchaseDetails;
    public final StringBuilder promotionalDetails;
    public int totalPrice;
    public int totalPromotionalDiscount;
    public int totalFinalQuantity;

    private PurchaseProcessData() {
        this.productsToSave = new ArrayList<>();
        this.purchaseDetails = new StringBuilder();
        this.promotionalDetails = new StringBuilder();
        this.totalPrice = INITIAL_TOTAL_PRICE;
        this.totalPromotionalDiscount = INITIAL_PROMOTIONAL_DISCOUNT;
        this.totalFinalQuantity = INITIAL_FINAL_QUANTITY;
    }

    public static PurchaseProcessData of() {
        return new PurchaseProcessData();
    }
}
