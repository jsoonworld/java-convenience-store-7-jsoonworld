package store.dto.response;

public class ReceiptResponse {
    private final int finalPrice;
    private final int membershipDiscount;

    private ReceiptResponse(int finalPrice, int membershipDiscount) {
        this.finalPrice = finalPrice;
        this.membershipDiscount = membershipDiscount;
    }

    public static ReceiptResponse of(int finalPrice, int membershipDiscount) {
        return new ReceiptResponse(finalPrice, membershipDiscount);
    }

    public int getFinalPrice() { return finalPrice; }
    public int getMembershipDiscount() { return membershipDiscount; }
}
