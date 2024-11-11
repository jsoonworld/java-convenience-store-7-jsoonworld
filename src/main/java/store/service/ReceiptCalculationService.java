package store.service;

import store.dto.request.ReceiptRequest;
import store.dto.response.ReceiptResponse;

public class ReceiptCalculationService {

    private static final double MEMBERSHIP_DISCOUNT_RATE = 0.3;
    private static final int MAX_MEMBERSHIP_DISCOUNT = 8000;

    public ReceiptResponse calculate(ReceiptRequest request) {
        int membershipDiscount = calculateMembershipDiscount(request);
        int finalPrice = request.getTotalPrice() - request.getTotalPromotionalDiscount() - membershipDiscount;
        return ReceiptResponse.of(finalPrice, membershipDiscount);
    }

    private int calculateMembershipDiscount(ReceiptRequest request) {
        if (!request.isMembershipDiscountApplied()) {
            return 0;
        }
        return (int) Math.min(request.getTotalPrice() * MEMBERSHIP_DISCOUNT_RATE, MAX_MEMBERSHIP_DISCOUNT);
    }
}
