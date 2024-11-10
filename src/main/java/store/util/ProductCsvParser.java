package store.util;

import store.domain.Product;
import store.domain.vo.ProductName;
import store.domain.vo.Price;
import store.domain.vo.Quantity;
import store.domain.vo.PromotionName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ProductCsvParser {

    private static final int HEADER_INDEX = 0;
    private static final int DATA_START_INDEX = 1;
    private static final int NAME_INDEX = 0;
    private static final int PRICE_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;
    private static final int PROMOTION_NAME_INDEX = 3;
    private static final String DEFAULT_PROMOTION_NAME = "null";
    private static final String DELIMITER = ",";

    private String header;

    public List<Product> parseProducts(List<String> lines) {
        header = extractHeader(lines);
        List<String> dataLines = extractDataLines(lines);

        return dataLines.stream()
                .map(this::parseProduct)
                .collect(Collectors.toList());
    }

    public List<String> toCsvWithHeader(List<Product> products) {
        List<String> csvLines = new ArrayList<>();
        csvLines.add(header);
        csvLines.addAll(products.stream()
                .map(this::toCsvLine)
                .collect(Collectors.toList()));
        return csvLines;
    }

    private String toCsvLine(Product product) {
        String promotionName = getPromotionName(product);
        return String.join(DELIMITER,
                product.getName(),
                String.valueOf(product.getPriceValue()),
                String.valueOf(product.getQuantityValue()),
                promotionName);
    }

    private String extractHeader(List<String> lines) {
        return lines.get(HEADER_INDEX);
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

    private String getPromotionName(Product product) {
        if (product.isPromotional()) {
            return product.getPromotionName();
        }
        return DEFAULT_PROMOTION_NAME;
    }

    private ProductName parseName(List<String> fields) {
        return ProductName.from(fields.get(NAME_INDEX));
    }

    private Price parsePrice(List<String> fields) {
        return Price.from(Integer.parseInt(fields.get(PRICE_INDEX)));
    }

    private Quantity parseQuantity(List<String> fields) {
        return Quantity.from(Integer.parseInt(fields.get(QUANTITY_INDEX)));
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
