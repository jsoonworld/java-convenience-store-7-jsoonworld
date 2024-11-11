package store.util.formatter;

import store.dto.request.ReceiptRequest;
import store.dto.response.ReceiptResponse;

public class ReceiptFormatter {

    public static String formatReceipt(ReceiptRequest request, ReceiptResponse response) {
        StringBuilder receiptBuilder = new StringBuilder();

        receiptBuilder.append("\n===========W 편의점=============\n");
        receiptBuilder.append("상품명\t\t수량\t금액\n");
        receiptBuilder.append(request.getPurchaseDetails().toString());

        receiptBuilder.append("===========증    정=============\n");
        receiptBuilder.append(request.getPromotionalDetails().toString());
        receiptBuilder.append("===============================\n");

        receiptBuilder.append(String.format("총구매액\t\t%d\t%,d원\n", request.getTotalFinalQuantity(), request.getTotalPrice()));
        receiptBuilder.append(String.format("행사할인\t\t\t%,d원\n", request.getTotalPromotionalDiscount()));

        if (request.isMembershipDiscountApplied()) {
            receiptBuilder.append(String.format("멤버십할인\t\t\t-%,d원\n", response.getMembershipDiscount()));
        }

        receiptBuilder.append(String.format("내실돈\t\t\t%,d원\n", response.getFinalPrice()));
        receiptBuilder.append("===============================\n");

        return receiptBuilder.toString();
    }
}
