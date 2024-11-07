package store.repository;

import store.io.FileDataLoader;

import java.io.IOException;
import java.util.List;

public class ProductFileRepository implements Repository<String> {
    private final List<String> products;

    public ProductFileRepository(FileDataLoader fileDataLoader, String productFilePath) throws IOException {
        this.products = fileDataLoader.loadFile(productFilePath);
    }

    @Override
    public List<String> findAll() {
        return products;
    }
}
