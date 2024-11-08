package store.domain.vo;

import static store.exception.ErrorMessage.NEGATIVE_FREE_QUANTITY;

public class FreeQuantity {
    private static final int MINIMUM_FREE_QUANTITY = 0;

    private final int value;

    private FreeQuantity(int value) {
        validateNonNegative(value);
        this.value = value;
    }

    public static FreeQuantity from(int value) {
        return new FreeQuantity(value);
    }

    public int value() {
        return value;
    }

    private void validateNonNegative(int value) {
        if (value < MINIMUM_FREE_QUANTITY) {
            throw new IllegalArgumentException(NEGATIVE_FREE_QUANTITY.getMessage());
        }
    }
}
