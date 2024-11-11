package store.view;

import store.dto.request.ReceiptRequest;
import store.dto.response.ReceiptResponse;
import store.util.formatter.ReceiptFormatter;

import java.util.List;

public class OutputView {
    private static final String WELCOME_MESSAGE = "안녕하세요. W편의점입니다.";
    private static final String CURRENT_PRODUCTS_MESSAGE = "현재 보유하고 있는 상품입니다.";
    private static final String PURCHASE_PROMPT = "구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])";
    private static final String MEMBERSHIP_PROMPT = "멤버십 할인을 받으시겠습니까? (Y/N)";
    private static final String PROMOTION_PROMPT_TEMPLATE = "현재 %s은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)";
    private static final String ADDITIONAL_PURCHASE_PROMPT = "감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)";


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

    public void printMembershipPrompt() {
        System.out.println(MEMBERSHIP_PROMPT);
    }

    public void printReceipt(ReceiptRequest request, ReceiptResponse response) {
        String formattedReceipt = ReceiptFormatter.formatReceipt(request, response);
        System.out.println(formattedReceipt);
    }

    public void printPromotionPrompt(String productName) {
        System.out.printf(PROMOTION_PROMPT_TEMPLATE + "%n", productName);
    }

    public void printContinuePurchasePrompt() {
        System.out.println(ADDITIONAL_PURCHASE_PROMPT);
    }
}
