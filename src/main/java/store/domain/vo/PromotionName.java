package store.domain.vo;

import static store.exception.ErrorMessage.*;

import java.util.Objects;

public class PromotionName {
    private final String value;

    private PromotionName(String value) {
        validate(value);
        this.value = value;
    }

    public static PromotionName from(String value) {
        return new PromotionName(value);
    }

    public String value() {
        return value;
    }

    public boolean isNullPromotion() {
        return "null".equalsIgnoreCase(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PromotionName that)) {
            return false;
        }
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    private void validate(String value) {
        validateNotNull(value);
    }

    private void validateNotNull(String value) {
        if (value == null) {
            throw new IllegalArgumentException(INVALID_PROMOTION_NAME.getMessage());
        }
    }
}
