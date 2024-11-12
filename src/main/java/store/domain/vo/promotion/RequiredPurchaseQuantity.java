package store.domain.vo.promotion;

import static store.exception.ErrorMessage.NEGATIVE_PROMOTION_QUANTITY;

public class RequiredPurchaseQuantity {
    private static final int MINIMUM_QUANTITY = 0;

    private final int value;

    private RequiredPurchaseQuantity(int value) {
        validateMinimumQuantity(value);
        this.value = value;
    }

    public static RequiredPurchaseQuantity from(int value) {
        return new RequiredPurchaseQuantity(value);
    }

    public int getValue() {
        return value;
    }

    private void validateMinimumQuantity(int value) {
        if (value < MINIMUM_QUANTITY) {
            throw new IllegalArgumentException(NEGATIVE_PROMOTION_QUANTITY.getMessage());
        }
    }
}
