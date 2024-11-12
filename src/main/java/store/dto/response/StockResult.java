package store.dto.response;

import store.domain.product.Product;

import java.util.List;

public class StockResult {
    private final int totalPromotionalDiscount;
    private final List<Product> productsToSave;

    private StockResult(int totalPromotionalDiscount, List<Product> productsToSave) {
        this.totalPromotionalDiscount = totalPromotionalDiscount;
        this.productsToSave = productsToSave;
    }

    public static StockResult of(int totalPromotionalDiscount, List<Product> productsToSave) {
        return new StockResult(totalPromotionalDiscount, productsToSave);
    }

    public int getTotalPromotionalDiscount() {
        return totalPromotionalDiscount;
    }

    public List<Product> getProductsToSave() {
        return productsToSave;
    }
}
