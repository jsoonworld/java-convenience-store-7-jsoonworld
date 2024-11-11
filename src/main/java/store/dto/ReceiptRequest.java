package store.dto;

public class ReceiptRequest {
    private final StringBuilder purchaseDetails;
    private final StringBuilder promotionalDetails;
    private final int totalFinalQuantity;
    private final int totalPrice;
    private final int totalPromotionalDiscount;
    private final boolean isMembershipDiscountApplied;

    private ReceiptRequest(StringBuilder purchaseDetails, StringBuilder promotionalDetails, int totalFinalQuantity, int totalPrice, int totalPromotionalDiscount, boolean isMembershipDiscountApplied) {
        this.purchaseDetails = purchaseDetails;
        this.promotionalDetails = promotionalDetails;
        this.totalFinalQuantity = totalFinalQuantity;
        this.totalPrice = totalPrice;
        this.totalPromotionalDiscount = totalPromotionalDiscount;
        this.isMembershipDiscountApplied = isMembershipDiscountApplied;
    }

    public static ReceiptRequest of(StringBuilder purchaseDetails, StringBuilder promotionalDetails, int totalFinalQuantity, int totalPrice, int totalPromotionalDiscount, boolean isMembershipDiscountApplied) {
        return new ReceiptRequest(purchaseDetails, promotionalDetails, totalFinalQuantity, totalPrice, totalPromotionalDiscount, isMembershipDiscountApplied);
    }

    public StringBuilder getPurchaseDetails() { return purchaseDetails; }
    public StringBuilder getPromotionalDetails() { return promotionalDetails; }
    public int getTotalFinalQuantity() { return totalFinalQuantity; }
    public int getTotalPrice() { return totalPrice; }
    public int getTotalPromotionalDiscount() { return totalPromotionalDiscount; }
    public boolean isMembershipDiscountApplied() { return isMembershipDiscountApplied; }
}
