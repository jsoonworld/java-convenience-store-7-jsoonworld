package store.view;

import camp.nextstep.edu.missionutils.Console;
import store.dto.request.purchase.AdditionalPurchaseInput;
import store.dto.request.MembershipDiscountInput;
import store.dto.request.promotion.PromotionConfirmationInput;
import store.dto.request.purchase.PurchaseInput;

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
