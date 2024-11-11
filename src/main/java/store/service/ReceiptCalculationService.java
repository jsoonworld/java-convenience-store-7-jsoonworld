package store.service;

import store.dto.ReceiptRequest;
import store.dto.ReceiptResponse;

public class ReceiptCalculationService {

    public ReceiptResponse calculate(ReceiptRequest request) {
        int membershipDiscount = 0;
        if (request.isMembershipDiscountApplied()) {
            membershipDiscount = (int) Math.min(request.getTotalPrice() * 0.3, 8000);
        }
        int finalPrice = request.getTotalPrice() - request.getTotalPromotionalDiscount() - membershipDiscount;
        return ReceiptResponse.of(finalPrice, membershipDiscount);
    }
}
