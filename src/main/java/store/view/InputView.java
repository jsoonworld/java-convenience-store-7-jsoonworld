package store.view;

import camp.nextstep.edu.missionutils.Console;
import store.dto.request.AdditionalPurchaseInput;
import store.dto.request.MembershipDiscountInput;
import store.dto.request.PromotionConfirmationInput;
import store.dto.request.PurchaseInput;

public class InputView {

    public PurchaseInput readPurchaseInput() {
        String input = Console.readLine();
        return PurchaseInput.from(input);
    }

    public MembershipDiscountInput readMembershipDiscount() {
        String membershipInput = Console.readLine();
        return MembershipDiscountInput.from(membershipInput);
    }

    public PromotionConfirmationInput readPromotionConfirmation() {
        String userResponse = Console.readLine();
        return PromotionConfirmationInput.from(userResponse);
    }

    public AdditionalPurchaseInput readAdditionalPurchaseInput() {
        String input = Console.readLine();
        return AdditionalPurchaseInput.from(input);
    }
}
