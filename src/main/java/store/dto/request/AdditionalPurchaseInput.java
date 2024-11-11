package store.dto.request;

import static store.exception.ErrorMessage.*;

public class AdditionalPurchaseInput {
    private final boolean wantsToContinue;

    private AdditionalPurchaseInput(boolean wantsToContinue) {
        this.wantsToContinue = wantsToContinue;
    }

    public static AdditionalPurchaseInput from(String input) {
        if (!"Y".equalsIgnoreCase(input.trim()) && !"N".equalsIgnoreCase(input.trim())) {
            throw new IllegalArgumentException(INVALID_INPUT_MESSAGE.getMessage());
        }
        return new AdditionalPurchaseInput("Y".equalsIgnoreCase(input.trim()));
    }

    public boolean wantsToContinue() {
        return wantsToContinue;
    }
}
