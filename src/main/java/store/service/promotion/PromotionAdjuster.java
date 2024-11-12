package store.service.promotion;

import store.dto.init.PromotionCalculationData;
import store.dto.request.promotion.PromotionRequest;
import store.dto.request.promotion.PromotionConfirmationInput;
import store.view.InputView;
import store.view.OutputView;

public class PromotionAdjuster {

    private static final String CONFIRMATION_YES = "Y";

    private final InputView inputView;
    private final OutputView outputView;

    public PromotionAdjuster(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public PromotionCalculationData handleAdditionalPurchase(PromotionRequest request, PromotionCalculationData dto) {
        int requestedQuantity = request.getRequestedQuantity();
        int buyQuantity = dto.getBuyQuantity();

        if (requestedQuantity >= buyQuantity) {
            return processAdditionalPurchase(request, dto, requestedQuantity - buyQuantity);
        }
        return dto;
    }

    private PromotionCalculationData processAdditionalPurchase(PromotionRequest request, PromotionCalculationData dto, int unmetPromotionQuantity) {
        while (true) {
            if (confirmAdditionalPurchase(request)) {
                return updateWithAdditionalPurchase(dto, request, unmetPromotionQuantity);
            }
        }
    }

    private boolean confirmAdditionalPurchase(PromotionRequest request) {
        try {
            outputView.printPromotionPrompt(request.getProductName());
            PromotionConfirmationInput confirmationInput = inputView.readPromotionConfirmation();
            return CONFIRMATION_YES.equals(confirmationInput.getUserResponse());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    private PromotionCalculationData updateWithAdditionalPurchase(PromotionCalculationData dto, PromotionRequest request, int unmetPromotionQuantity) {
        int updatedFinalQuantity = dto.getFinalQuantity() + unmetPromotionQuantity;
        int freeItems = (updatedFinalQuantity / dto.getBuyQuantity()) * dto.getFreeQuantity();

        PromotionCalculationData updatedDto = dto.withUpdatedQuantities(updatedFinalQuantity, freeItems);
        PromotionStockManager.addProducts(updatedDto, request);
        return updatedDto;
    }
}
