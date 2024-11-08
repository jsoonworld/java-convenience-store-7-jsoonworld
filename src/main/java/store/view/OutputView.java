package store.view;

import java.util.List;

public class OutputView {
    private static final String WELCOME_MESSAGE = "안녕하세요. W편의점입니다.";
    private static final String CURRENT_PRODUCTS_MESSAGE = "현재 보유하고 있는 상품입니다.";
    private static final String PURCHASE_PROMPT = "구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])";

    public void printWelcomeMessage() {
        System.out.println(WELCOME_MESSAGE);
    }

    public void printCurrentProductsMessage() {
        System.out.println(CURRENT_PRODUCTS_MESSAGE);
    }

    public void printProducts(List<String> formattedProducts) {
        System.out.println();
        formattedProducts.forEach(System.out::println);
    }

    public void printPurchasePrompt() {
        System.out.println();
        System.out.println(PURCHASE_PROMPT);
    }
}
