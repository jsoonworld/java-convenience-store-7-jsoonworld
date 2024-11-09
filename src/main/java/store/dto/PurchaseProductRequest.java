package store.dto;

import store.domain.PurchaseProduct;
import store.domain.vo.PurchaseProductName;
import store.domain.vo.PurchaseQuantity;

public class PurchaseProductRequest {
    private final String productName;
    private final int quantity;

    private PurchaseProductRequest(String productName, int quantity) {
        this.productName = productName;
        this.quantity = quantity;
    }

    public static PurchaseProductRequest of(String productName, int quantity) {
        return new PurchaseProductRequest(productName, quantity);
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public PurchaseProduct toPurchaseProduct() {
        return new PurchaseProduct(
                PurchaseProductName.of(productName),
                PurchaseQuantity.of(quantity)
        );
    }
}
