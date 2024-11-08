package store.view;

import java.util.List;

public class OutputView {
    private static final String WELCOME_MESSAGE = "안녕하세요. W편의점입니다.";
    private static final String CURRENT_PRODUCTS_MESSAGE = "현재 보유하고 있는 상품입니다.";

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
}
