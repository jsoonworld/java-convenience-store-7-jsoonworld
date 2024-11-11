package store.config;

import store.util.ProductCsvParser;
import store.util.PromotionCsvParser;

public class ParserConfig {
    private final ProductCsvParser productCsvParser;
    private final PromotionCsvParser promotionCsvParser;

    public ParserConfig() {
        this.productCsvParser = new ProductCsvParser();
        this.promotionCsvParser = new PromotionCsvParser();
    }

    public ProductCsvParser getProductCsvParser() {
        return productCsvParser;
    }

    public PromotionCsvParser getPromotionCsvParser() {
        return promotionCsvParser;
    }
}