package store.dto.request.purchase;

import static store.exception.ErrorMessage.*;

public class PurchaseInput {
    private final String input;

    private PurchaseInput(String input) {
        validate(input);
        this.input = input;
    }

    public static PurchaseInput from(String input) {
        return new PurchaseInput(input);
    }

    public String getInput() {
        return input;
    }

    private void validate(String input) {
        validateNotNull(input);
        validateNotEmpty(input);

    }

    private void validateNotNull(String input) {
        if (input == null) {
            throw new IllegalArgumentException(PURCHASE_INPUT_CANNOT_BE_NULL.getMessage());
        }
    }

    private void validateNotEmpty(String input) {
        if (input.isBlank()) {
            throw new IllegalArgumentException(PURCHASE_INPUT_CANNOT_BE_EMPTY.getMessage());
        }
    }
}
