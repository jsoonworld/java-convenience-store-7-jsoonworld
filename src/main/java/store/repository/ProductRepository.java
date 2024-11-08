package store.repository;

import store.io.FileDataLoader;

public class ProductRepository extends FileBasedRepository {

    public ProductRepository(FileDataLoader dataLoader, String filePath) {
        super(dataLoader, filePath);
    }
}
