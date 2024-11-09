package store.controller;

import store.domain.Product;
import store.repository.ProductRepository;
import store.util.ProductInventoryFormatter;

import java.util.List;

public class ProductController {

    private final ProductRepository repository;

    public ProductController(ProductRepository repository) {
        this.repository = repository;
    }

    public List<String> getFormattedProducts() {
        List<Product> products = repository.getAllProducts();
        return ProductInventoryFormatter.format(products);
    }
}
