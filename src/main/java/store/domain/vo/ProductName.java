package store.domain.vo;

import static store.exception.ErrorMessage.*;

import java.util.Objects;

public class ProductName {
    private final String name;

    private ProductName(String name) {
        validate(name);
        this.name = name;
    }

    public static ProductName from(String name) {
        return new ProductName(name);
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductName that)) return false;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    private void validate(String name) {
        validateNotNull(name);
        validateNotBlank(name);
    }

    private void validateNotNull(String name) {
        if (name == null) {
            throw new IllegalArgumentException(NULL_PRODUCT_NAME.getMessage());
        }
    }

    private void validateNotBlank(String name) {
        if (name.isBlank()) {
            throw new IllegalArgumentException(EMPTY_PRODUCT_NAME.getMessage());
        }
    }
}
