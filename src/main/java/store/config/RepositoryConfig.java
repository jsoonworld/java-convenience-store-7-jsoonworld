package store.config;

import store.domain.product.Product;
import store.domain.promotion.Promotion;
import store.exception.ErrorMessage;
import store.repository.ProductRepository;
import store.repository.PromotionRepository;
import store.io.input.FileDataLoader;
import store.util.parser.ProductCsvParser;
import store.util.parser.PromotionCsvParser;

import java.io.IOException;
import java.util.List;

import static store.exception.ErrorMessage.*;

public class RepositoryConfig {

    private final FileDataLoader dataLoader;
    private final String productFilePath;
    private final String promotionFilePath;
    private final ProductCsvParser productCsvParser;
    private final PromotionCsvParser promotionCsvParser;

    public RepositoryConfig(FileConfig fileConfig, ParserConfig parserConfig) {
        this.dataLoader = fileConfig.getDataLoader();
        this.productFilePath = fileConfig.getProductFilePath();
        this.promotionFilePath = fileConfig.getPromotionFilePath();
        this.productCsvParser = parserConfig.getProductCsvParser();
        this.promotionCsvParser = parserConfig.getPromotionCsvParser();
    }

    public ProductRepository initializeProductRepository() {
        try {
            List<String> rawData = dataLoader.loadFile(productFilePath);
            List<Product> parsedProducts = productCsvParser.parseProducts(rawData);
            return new ProductRepository(parsedProducts);
        } catch (IOException e) {
            throw new RuntimeException(PRODUCT_DATA_INITIALIZATION_ERROR.getMessage());
        }
    }

    public PromotionRepository initializePromotionRepository() {
        try {
            List<String> rawData = dataLoader.loadFile(promotionFilePath);
            List<Promotion> parsedPromotions = promotionCsvParser.parsePromotions(rawData);
            return new PromotionRepository(parsedPromotions);
        } catch (IOException e) {
            throw new RuntimeException(PROMOTION_DATA_INITIALIZATION_ERROR.getMessage());
        }

    }
}
