package store.dto.request.promotion;

public class PromotionConfirmationInput {

    private final String userResponse;

    private PromotionConfirmationInput(String userResponse) {
        this.userResponse = userResponse.trim().toUpperCase();
    }

    public static PromotionConfirmationInput from(String userResponse) {
        return new PromotionConfirmationInput(userResponse);
    }

    public String getUserResponse() {
        return userResponse;
    }
}