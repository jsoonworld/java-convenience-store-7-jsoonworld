package store.validation;

import static store.exception.ErrorMessage.*;

public class PurchaseInputValidator {

    private static final String ITEM_SEPARATOR = ",";
    private static final String BRACKET_LEFT = "[";
    private static final String BRACKET_RIGHT = "]";
    private static final String PART_SEPARATOR = "-";
    private static final int VALID_PARTS_COUNT = 2;
    private static final int BRACKET_TRIM_START = 1;
    private static final int BRACKET_TRIM_END_OFFSET = 1;
    private static final int MINIMUM_QUANTITY = 1;
    private static final int NAME_INDEX = 0;
    private static final int QUANTITY_INDEX = 1;

    public static void validate(String input) {
        validateNotNull(input);
        validateNotEmpty(input);
        validateFormatNotStartingOrEndingWithComma(input);
        validateItems(input);
    }

    private static void validateNotNull(String input) {
        if (input == null) {
            throw new IllegalArgumentException(PURCHASE_INPUT_CANNOT_BE_NULL.getMessage());
        }
    }

    private static void validateNotEmpty(String input) {
        if (input.isBlank()) {
            throw new IllegalArgumentException(PURCHASE_INPUT_CANNOT_BE_EMPTY.getMessage());
        }
    }

    private static void validateFormatNotStartingOrEndingWithComma(String input) {
        if (input.startsWith(ITEM_SEPARATOR) || input.endsWith(ITEM_SEPARATOR)) {
            throw new IllegalArgumentException(INPUT_CANNOT_START_OR_END_WITH_COMMA.getMessage());
        }
    }

    private static void validateItems(String input) {
        String[] items = input.split(ITEM_SEPARATOR);
        for (String item : items) {
            validateItemNotEmpty(item);
            validateItemBrackets(item);
            validateItemStructure(item);
        }
    }

    private static void validateItemNotEmpty(String item) {
        if (item.trim().isEmpty()) {
            throw new IllegalArgumentException(EMPTY_ITEM_INCLUDED.getMessage());
        }
    }

    private static void validateItemBrackets(String item) {
        String trimmedItem = item.trim();
        if (!trimmedItem.startsWith(BRACKET_LEFT) || !trimmedItem.endsWith(BRACKET_RIGHT)) {
            throw new IllegalArgumentException(ITEM_MUST_BE_IN_BRACKETS.getMessage());
        }
    }

    private static void validateItemStructure(String item) {
        String content = extractContent(item);
        String[] parts = content.split(PART_SEPARATOR, -1);
        validateHyphenStructure(parts);
        validateNameNotEmpty(parts[NAME_INDEX].trim());
        validateQuantity(parts[QUANTITY_INDEX].trim());
    }

    private static String extractContent(String item) {
        String trimmedItem = item.trim();
        return trimmedItem.substring(BRACKET_TRIM_START, trimmedItem.length() - BRACKET_TRIM_END_OFFSET);
    }

    private static void validateHyphenStructure(String[] parts) {
        if (parts.length != VALID_PARTS_COUNT) {
            throw new IllegalArgumentException(ITEM_MUST_CONTAIN_NAME_AND_QUANTITY.getMessage());
        }
    }

    private static void validateNameNotEmpty(String name) {
        if (name.isEmpty()) {
            throw new IllegalArgumentException(PRODUCT_NAME_CANNOT_BE_EMPTY.getMessage());
        }
    }

    private static void validateQuantity(String quantityStr) {
        validateQuantityNotEmpty(quantityStr);
        validateQuantityIsNumberAndPositive(quantityStr);
    }

    private static void validateQuantityNotEmpty(String quantityStr) {
        if (quantityStr.isEmpty()) {
            throw new IllegalArgumentException(QUANTITY_CANNOT_BE_EMPTY.getMessage());
        }
    }

    private static void validateQuantityIsNumberAndPositive(String quantityStr) {
        try {
            int quantity = Integer.parseInt(quantityStr);
            if (quantity < MINIMUM_QUANTITY) {
                throw new IllegalArgumentException(QUANTITY_MUST_BE_POSITIVE.getMessage());
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(QUANTITY_MUST_BE_NUMERIC.getMessage());
        }
    }
}
