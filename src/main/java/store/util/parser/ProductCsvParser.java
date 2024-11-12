package store.util.parser;

import store.domain.product.Product;
import store.domain.vo.ProductName;
import store.domain.vo.Price;
import store.domain.vo.Quantity;
import store.domain.vo.PromotionName;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ProductCsvParser {

    private static final int DATA_START_INDEX = 1;
    private static final int NAME_INDEX = 0;
    private static final int PRICE_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;
    private static final int PROMOTION_NAME_INDEX = 3;
    private static final String DEFAULT_PROMOTION_NAME = "null";
    private static final String DELIMITER = ",";
    private static final String NO_STOCK = "재고 없음";
    private static final int DEFAULT_QUANTITY = 0;

    public List<Product> parseProducts(List<String> lines) {
        List<String> dataLines = extractDataLines(lines);

        return dataLines.stream()
                .map(this::parseProduct)
                .collect(Collectors.toList());
    }

    private List<String> extractDataLines(List<String> lines) {
        return lines.subList(DATA_START_INDEX, lines.size());
    }

    private Product parseProduct(String line) {
        List<String> fields = Arrays.asList(line.split(DELIMITER));
        ProductName productName = parseName(fields);
        Price price = parsePrice(fields);
        Quantity quantity = parseQuantity(fields);
        PromotionName promotionName = createPromotionName(fields);

        return Product.of(productName, price, quantity, promotionName);
    }

    private ProductName parseName(List<String> fields) {
        return ProductName.from(fields.get(NAME_INDEX));
    }

    private Price parsePrice(List<String> fields) {
        return Price.from(Integer.parseInt(fields.get(PRICE_INDEX)));
    }

    private Quantity parseQuantity(List<String> fields) {
        String quantityField = fields.get(QUANTITY_INDEX);
        int quantityValue = parseQuantityValue(quantityField);
        return Quantity.from(quantityValue);
    }

    private int parseQuantityValue(String quantityField) {
        if (NO_STOCK.equals(quantityField)) {
            return DEFAULT_QUANTITY;
        }
        return Integer.parseInt(quantityField);
    }


    private PromotionName createPromotionName(List<String> fields) {
        String promotionField = getPromotionField(fields);
        if (isNullPromotion(promotionField)) {
            return PromotionName.from(DEFAULT_PROMOTION_NAME);
        }
        return PromotionName.from(promotionField);
    }

    private String getPromotionField(List<String> fields) {
        if (fields.size() > PROMOTION_NAME_INDEX) {
            return fields.get(PROMOTION_NAME_INDEX);
        }
        return DEFAULT_PROMOTION_NAME;
    }

    private boolean isNullPromotion(String promotionField) {
        return DEFAULT_PROMOTION_NAME.equalsIgnoreCase(promotionField);
    }
}
