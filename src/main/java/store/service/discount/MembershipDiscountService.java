package store.service.discount;

import store.view.InputView;
import store.view.OutputView;
import store.dto.request.MembershipDiscountInput;

public class MembershipDiscountService {

    private final OutputView outputView;
    private final InputView inputView;

    public MembershipDiscountService(OutputView outputView, InputView inputView) {
        this.outputView = outputView;
        this.inputView = inputView;
    }

    public boolean applyDiscount() {
        while (true) {
            try {
                outputView.printMembershipPrompt();
                MembershipDiscountInput membershipDiscountResponse = inputView.readMembershipDiscount();
                return membershipDiscountResponse.isDiscountApplied();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
