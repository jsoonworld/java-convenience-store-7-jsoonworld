package store.domain.purchase;

import store.domain.vo.PurchaseProductName;
import store.domain.vo.PurchaseQuantity;

public class PurchaseProduct {
    private final PurchaseProductName name;
    private final PurchaseQuantity quantity;

    public PurchaseProduct(PurchaseProductName name, PurchaseQuantity quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getPurchasedProductName() {
        return name.getName();
    }

    public int getPurchasedQuantityValue() {
        return quantity.getAmount();
    }
}
