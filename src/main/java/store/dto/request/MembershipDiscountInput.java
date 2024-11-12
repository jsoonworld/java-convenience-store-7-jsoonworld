package store.dto.request;

public class MembershipDiscountInput {
    private static final String INVALID_INPUT_MESSAGE = "[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.";
    private final boolean isDiscountApplied;

    private MembershipDiscountInput(boolean isDiscountApplied) {
        this.isDiscountApplied = isDiscountApplied;
    }

    public static MembershipDiscountInput from(String input) {
        validateInput(input);
        return new MembershipDiscountInput("Y".equalsIgnoreCase(input.trim()));
    }

    public boolean isDiscountApplied() {
        return isDiscountApplied;
    }

    private static void validateInput(String input) {
        if (!"Y".equalsIgnoreCase(input.trim()) && !"N".equalsIgnoreCase(input.trim())) {
            throw new IllegalArgumentException(INVALID_INPUT_MESSAGE);
        }
    }
}
