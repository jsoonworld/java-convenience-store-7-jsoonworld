package store.dto.request;

import store.domain.Product;
import java.util.List;

public class ProductSaveRequest {
    private final List<Product> productsToSave;

    private ProductSaveRequest(List<Product> productsToSave) {
        this.productsToSave = productsToSave;
    }

    public static ProductSaveRequest of(List<Product> productsToSave) {
        return new ProductSaveRequest(productsToSave);
    }

    public List<Product> getProductsToSave() {
        return productsToSave;
    }
}
