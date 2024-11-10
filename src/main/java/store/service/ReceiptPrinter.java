package store.service;

public class ReceiptPrinter {

    public void printReceipt(StringBuilder purchaseDetails, StringBuilder promotionalDetails, int totalFinalQuantity, int totalPrice, int totalPromotionalDiscount, boolean isMembershipDiscountApplied) {
        int membershipDiscount = 0;
        if (isMembershipDiscountApplied) {
            membershipDiscount = (int) Math.min(totalPrice * 0.3, 8000); // 최대 30% 할인, 최대 8000원
        }
        int finalPrice = totalPrice - totalPromotionalDiscount - membershipDiscount;

        System.out.println("\n===========W 편의점=============");
        System.out.println("상품명\t\t수량\t금액");
        System.out.print(purchaseDetails.toString());

        System.out.println("===========증    정=============");
        System.out.print(promotionalDetails.toString());
        System.out.println("===============================");

        System.out.printf("총구매액\t\t%d\t%,d원\n", totalFinalQuantity, totalPrice);
        System.out.printf("행사할인\t\t\t-%,d원\n", totalPromotionalDiscount);
        if (isMembershipDiscountApplied) {
            System.out.printf("멤버십할인\t\t\t-%,d원\n", membershipDiscount);
        }
        System.out.printf("내실돈\t\t\t%,d원\n", finalPrice);
        System.out.println("===============================");
    }
}
