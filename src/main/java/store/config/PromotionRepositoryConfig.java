package store.config;

import static store.exception.ErrorMessage.*;

import store.domain.Promotion;
import store.io.FileDataLoader;
import store.repository.PromotionRepository;
import store.util.PromotionCsvParser;

import java.io.IOException;
import java.util.List;

public class PromotionRepositoryConfig {
    private final FileDataLoader dataLoader;
    private final PromotionCsvParser parser;
    private final String filePath;

    public PromotionRepositoryConfig(FileDataLoader dataLoader, PromotionCsvParser parser, String filePath) {
        this.dataLoader = dataLoader;
        this.parser = parser;
        this.filePath = filePath;
    }

    public PromotionRepository initializePromotionRepository() {
        try {
            List<String> rawData = dataLoader.loadFile(filePath);
            List<Promotion> parsedPromotions = parser.parsePromotions(rawData);
            return new PromotionRepository(parsedPromotions);
        } catch (IOException e) {
            throw new RuntimeException(PROMOTION_DATA_INITIALIZATION_ERROR.getMessage());
        }
    }
}
