package store.util;

import store.domain.Product;
import store.domain.vo.ProductName;
import store.domain.vo.Price;
import store.domain.vo.Quantity;
import store.domain.vo.PromotionName;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ProductCsvParser {

    private static final int HEADER_LINE_COUNT = 1;
    private static final int NAME_INDEX = 0;
    private static final int PRICE_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;
    private static final int PROMOTION_NAME_INDEX = 3;

    public List<Product> parseProducts(List<String> lines) {
        return lines.stream()
                .skip(HEADER_LINE_COUNT)
                .map(this::parseProduct)
                .collect(Collectors.toList());
    }

    private Product parseProduct(String line) {
        List<String> fields = Arrays.asList(line.split(","));
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
        return Quantity.from(Integer.parseInt(fields.get(QUANTITY_INDEX)));
    }

    private PromotionName createPromotionName(List<String> fields) {
        String promotionField = getPromotionField(fields);
        if (isNullPromotion(promotionField)) {
            return PromotionName.from("null");
        }
        return PromotionName.from(promotionField);
    }

    private String getPromotionField(List<String> fields) {
        if (fields.size() > PROMOTION_NAME_INDEX) {
            return fields.get(PROMOTION_NAME_INDEX);
        }
        return "null";
    }

    private boolean isNullPromotion(String promotionField) {
        return "null".equalsIgnoreCase(promotionField);
    }

}
