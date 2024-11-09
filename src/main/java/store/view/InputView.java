package store.view;

import camp.nextstep.edu.missionutils.Console;
import store.dto.PurchaseInput;

public class InputView {

    public PurchaseInput readPurchaseInput() {
        String input = Console.readLine();
        return PurchaseInput.from(input);
    }
}
