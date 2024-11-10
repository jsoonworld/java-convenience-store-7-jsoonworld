package store.config;

import static store.exception.ErrorMessage.*;

import store.domain.Product;
import store.io.input.FileDataLoader;
import store.repository.ProductRepository;
import store.util.ProductCsvParser;

import java.io.IOException;
import java.util.List;

public class ProductRepositoryConfig {
    private final FileDataLoader dataLoader;
    private final ProductCsvParser parser;
    private final String filePath;

    public ProductRepositoryConfig(FileDataLoader dataLoader, ProductCsvParser parser, String filePath) {
        this.dataLoader = dataLoader;
        this.parser = parser;
        this.filePath = filePath;
    }

    public ProductRepository initializeProductRepository() {
        try {
            List<String> rawData = dataLoader.loadFile(filePath);
            List<Product> parsedProducts = parser.parseProducts(rawData);
            return new ProductRepository(parsedProducts);
        } catch (IOException e) {
            throw new RuntimeException(PRODUCT_DATA_INITIALIZATION_ERROR.getMessage());
        }
    }
}
