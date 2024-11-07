package store.repository;

import store.io.FileDataLoader;

import java.io.IOException;
import java.util.List;

public class PromotionFileRepository implements Repository<String> {
    private final List<String> promotions;

    public PromotionFileRepository(FileDataLoader fileDataLoader, String promotionFilePath) throws IOException {
        this.promotions = fileDataLoader.loadFile(promotionFilePath);
    }

    @Override
    public List<String> findAll() {
        return promotions;
    }
}
