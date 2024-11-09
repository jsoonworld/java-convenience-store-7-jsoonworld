package store.util;

import static store.exception.ErrorMessage.*;

import store.dto.PurchaseProductRequest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PurchaseProductRequestParser {

    private static final String ITEM_SEPARATOR = ",";
    private static final String BRACKET_REMOVE_REGEX = "[\\[\\]]";
    private static final String PART_SEPARATOR = "-";
    private static final int EXPECTED_PARTS_COUNT = 2;
    private static final int NAME_INDEX = 0;
    private static final int QUANTITY_INDEX = 1;

    public static List<PurchaseProductRequest> parse(String input) {
        return Arrays.stream(splitItems(input))
                .map(PurchaseProductRequestParser::parseItem)
                .collect(Collectors.toList());
    }

    private static String[] splitItems(String input) {
        return input.split(ITEM_SEPARATOR);
    }

    private static PurchaseProductRequest parseItem(String item) {
        String[] parts = cleanItem(item).split(PART_SEPARATOR, -1);
        validateItemParts(parts);

        String name = parts[NAME_INDEX].trim();
        int quantity = Integer.parseInt(parts[QUANTITY_INDEX].trim());

        return PurchaseProductRequest.of(name, quantity);
    }

    private static String cleanItem(String item) {
        return item.trim().replaceAll(BRACKET_REMOVE_REGEX, "");
    }

    private static void validateItemParts(String[] parts) {
        if (parts.length != EXPECTED_PARTS_COUNT) {
            throw new IllegalArgumentException(ITEM_MUST_CONTAIN_NAME_AND_QUANTITY.getMessage());
        }
    }
}
