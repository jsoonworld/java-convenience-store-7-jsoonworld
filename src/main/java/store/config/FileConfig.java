package store.config;

import static store.exception.ErrorMessage.*;

import store.io.input.DefaultFileDataLoader;
import store.io.input.FileDataLoader;
import store.util.file.FilePathLoader;

import java.io.IOException;

public class FileConfig {
    private final FileDataLoader dataLoader;
    private final FilePathLoader filePathLoader;

    public FileConfig() {
        this.dataLoader = new DefaultFileDataLoader();
        this.filePathLoader = new FilePathLoader();
    }

    public String getProductFilePath() {
        try {
            return filePathLoader.getFilePath("products.md");
        } catch (IOException e) {
            throw new RuntimeException(PRODUCT_FILE_PATH_LOAD_ERROR.getMessage());
        }
    }

    public String getPromotionFilePath() {
        try {
            return filePathLoader.getFilePath("promotions.md");
        } catch (IOException e) {
            throw new RuntimeException(PROMOTION_FILE_PATH_LOAD_ERROR.getMessage());
        }
    }


    public FileDataLoader getDataLoader() {
        return dataLoader;
    }
}