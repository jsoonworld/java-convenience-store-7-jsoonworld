package store.repository;

import store.domain.Product;
import java.util.List;

public class ProductRepository {
    private final List<Product> products;

    public ProductRepository(List<Product> products) {
        this.products = products;
    }

    public List<Product> getAllProducts() {
        return products;
    }
}
