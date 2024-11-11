package store.service;

import store.domain.Product;
import store.dto.request.ProductSaveRequest;
import store.repository.ProductRepository;

import java.util.List;

public class ProductSaver {
    private final ProductRepository productRepository;

    public ProductSaver(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void save(List<Product> productsToSave) {
        ProductSaveRequest saveRequest = ProductSaveRequest.of(productsToSave);
        saveRequest.getProductsToSave().forEach(productRepository::save);
    }
}
