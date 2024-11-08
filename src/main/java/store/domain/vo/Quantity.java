package store.domain.vo;

import static store.exception.ErrorMessage.*;

import java.util.Objects;

public class Quantity {
    private static final int MINIMUM_QUANTITY = 0;

    private final Integer amount;

    private Quantity(Integer amount) {
        validate(amount);
        this.amount = amount;
    }

    public static Quantity from(Integer amount) {
        return new Quantity(amount);
    }

    public Integer getAmount() {
        return amount;
    }

    public boolean isOutOfStock() {
        return amount != null && amount == MINIMUM_QUANTITY;
    }

    private void validate(Integer amount) {
        validateNotNull(amount);
        validateNonNegative(amount);
    }

    private void validateNotNull(Integer amount) {
        if (amount == null) {
            throw new IllegalArgumentException(NULL_QUANTITY.getMessage());
        }
    }

    private void validateNonNegative(Integer amount) {
        if (amount < MINIMUM_QUANTITY) {
            throw new IllegalArgumentException(NEGATIVE_QUANTITY.getMessage());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Quantity quantity)) {
            return false;
        }
        return Objects.equals(amount, quantity.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }
}
