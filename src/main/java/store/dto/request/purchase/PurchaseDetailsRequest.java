package store.dto.request.purchase;

import store.domain.Product;

public class PurchaseDetailsRequest {
    private final String productName;
    private final int quantity;
    private final int totalProductPrice;

    private PurchaseDetailsRequest(String productName, int quantity, int totalProductPrice) {
        this.productName = productName;
        this.quantity = quantity;
        this.totalProductPrice = totalProductPrice;
    }

    public static PurchaseDetailsRequest of(Product product, int quantity, int productPrice) {
        return new PurchaseDetailsRequest(product.getName(), quantity, productPrice * quantity);
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getTotalProductPrice() {
        return totalProductPrice;
    }
}
