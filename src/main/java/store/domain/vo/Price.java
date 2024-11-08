package store.domain.vo;

import static store.exception.ErrorMessage.*;

import java.util.Objects;

public class Price {
    private static final int MINIMUM_PRICE = 0;

    private final Integer value;

    private Price(Integer value) {
        validate(value);
        this.value = value;
    }

    public static Price from(Integer value) {
        return new Price(value);
    }

    public Integer getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Price price)) return false;
        return Objects.equals(value, price.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    private void validate(Integer value) {
        validateNotNull(value);
        validateNonNegative(value);
    }

    private void validateNotNull(Integer value) {
        if (value == null) {
            throw new IllegalArgumentException(NULL_PRICE.getMessage());
        }
    }

    private void validateNonNegative(Integer value) {
        if (value < MINIMUM_PRICE) {
            throw new IllegalArgumentException(NEGATIVE_PRICE.getMessage());
        }
    }
}
