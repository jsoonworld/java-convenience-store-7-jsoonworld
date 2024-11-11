package store.util.formatter;

import store.domain.product.Product;

import java.util.List;
import java.util.stream.Collectors;

public class ProductInventoryFormatter {

    private static final String CURRENCY_UNIT = "원";
    private static final String OUT_OF_STOCK_MESSAGE = "재고 없음";
    private static final String UNIT = "개";
    private static final String NULL_PROMOTION = "null";

    public static List<String> format(List<Product> products) {
        return products.stream()
                .map(ProductInventoryFormatter::formatSingleProduct)
                .collect(Collectors.toList());
    }

    private static String formatSingleProduct(Product product) {
        String name = formatName(product);
        String price = formatPrice(product);
        String quantityDisplay = formatQuantityDisplay(product);
        String promotionDisplay = formatPromotionDisplay(product);

        return buildDisplayString(name + " " + price, quantityDisplay, promotionDisplay);
    }

    private static String formatName(Product product) {
        return product.getName();
    }

    private static String formatPrice(Product product) {
        return String.format("%,d%s", product.getPriceValue(), CURRENCY_UNIT);
    }


    private static String formatQuantityDisplay(Product product) {
        if (product.hasNoStock()) {
            return OUT_OF_STOCK_MESSAGE;
        }
        return product.getQuantityValue() + UNIT;
    }

    private static String formatPromotionDisplay(Product product) {
        if (NULL_PROMOTION.equalsIgnoreCase(product.getPromotionName())) {
            return "";
        }
        return product.getPromotionName();
    }

    private static String buildDisplayString(String nameAndPrice, String quantityDisplay, String promotionDisplay) {
        if (promotionDisplay.isEmpty()) {
            return String.format("- %s %s", nameAndPrice, quantityDisplay);
        }
        return String.format("- %s %s %s", nameAndPrice, quantityDisplay, promotionDisplay);
    }
}
