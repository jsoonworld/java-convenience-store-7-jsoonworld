package store.domain.vo;

import static store.exception.ErrorMessage.*;

import java.util.Objects;

public class PurchaseQuantity {
    private final int amount;

    private PurchaseQuantity(int amount) {
        validateAmount(amount);
        this.amount = amount;
    }

    public static PurchaseQuantity of(int amount) {
        return new PurchaseQuantity(amount);
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PurchaseQuantity that)) return false;
        return amount == that.amount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }

    @Override
    public String toString() {
        return String.valueOf(amount);
    }

    private void validateAmount(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException(QUANTITY_MUST_BE_POSITIVE.getMessage());
        }
    }
}
