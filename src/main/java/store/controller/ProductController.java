package store.controller;

import store.domain.Product;
import store.repository.ProductRepository;
import store.util.ProductCsvParser;
import store.util.ProductInventoryFormatter;

import java.util.List;

public class ProductController {

    private final ProductRepository repository;
    private final ProductCsvParser parser;

    public ProductController(ProductRepository repository, ProductCsvParser parser) {
        this.repository = repository;
        this.parser = parser;
    }

    public List<String> getFormattedProducts() {
        List<String> lines = repository.loadAll();
        List<Product> products = parser.parseProducts(lines);
        return ProductInventoryFormatter.format(products);
    }
}
