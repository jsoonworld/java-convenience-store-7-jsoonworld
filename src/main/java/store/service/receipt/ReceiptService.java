package store.service.receipt;

import store.dto.request.ReceiptRequest;
import store.dto.response.ReceiptResponse;
import store.view.OutputView;

public class ReceiptService {

    private final ReceiptCalculationService calculationService;
    private final OutputView outputView;

    public ReceiptService(ReceiptCalculationService calculationService, OutputView outputView) {
        this.calculationService = calculationService;
        this.outputView = outputView;
    }

    public void printReceipt(StringBuilder purchaseDetails, StringBuilder promotionalDetails, int totalFinalQuantity, int totalPrice, int totalPromotionalDiscount, boolean membershipDiscountApplied) {
        ReceiptRequest receiptRequest = ReceiptRequest.of(
                purchaseDetails,
                promotionalDetails,
                totalFinalQuantity,
                totalPrice,
                totalPromotionalDiscount,
                membershipDiscountApplied
        );

        ReceiptResponse receiptResponse = calculationService.calculate(receiptRequest);
        outputView.printReceipt(receiptRequest, receiptResponse);
    }
}
