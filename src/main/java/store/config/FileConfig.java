package store.config;

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
            throw new RuntimeException("[ERROR] 상품 파일 경로를 불러오는 중 오류가 발생했습니다.");
        }
    }

    public String getPromotionFilePath() {
        try {
            return filePathLoader.getFilePath("promotions.md");
        } catch (IOException e) {
            throw new RuntimeException("[ERROR] 프로모션 파일 경로를 불러오는 중 오류가 발생했습니다.");
        }
    }

    public FileDataLoader getDataLoader() {
        return dataLoader;
    }
}