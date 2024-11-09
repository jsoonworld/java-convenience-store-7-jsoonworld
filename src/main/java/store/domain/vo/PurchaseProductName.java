package store.domain.vo;

import static store.exception.ErrorMessage.*;

import java.util.Objects;

public class PurchaseProductName {
    private final String name;

    private PurchaseProductName(String name) {
        validate(name);
        this.name = name;
    }

    public static PurchaseProductName of(String name) {
        return new PurchaseProductName(name);
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PurchaseProductName that)) return false;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name;
    }

    private void validate(String name) {
        validateNameNotNull(name);
        validateNameNotBlank(name);
    }

    private void validateNameNotNull(String name) {
        if (name == null) {
            throw new IllegalArgumentException(PRODUCT_NAME_CANNOT_BE_NULL.getMessage());
        }
    }

    private void validateNameNotBlank(String name) {
        if (name.isBlank()) {
            throw new IllegalArgumentException(PRODUCT_NAME_CANNOT_BE_BLANK.getMessage());
        }
    }
}
