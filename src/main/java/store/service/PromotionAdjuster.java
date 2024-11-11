package store.service;

import store.dto.init.PromotionCalculationData;
import store.dto.request.PromotionRequest;
import store.dto.request.PromotionConfirmationInput;
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
        if (request.getRequestedQuantity() < dto.getBuyQuantity()) {
            int unmetPromotionQuantity = dto.getBuyQuantity() - request.getRequestedQuantity();
            PromotionConfirmationInput confirmationInput;
            while (true) {
                try{
                    outputView.printPromotionPrompt(request.getProductName());
                    confirmationInput = inputView.readPromotionConfirmation();
                    if (CONFIRMATION_YES.equals(confirmationInput.getUserResponse())) {
                        return updateWithAdditionalPurchase(dto, request, unmetPromotionQuantity);
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return dto;
    }

    private PromotionCalculationData updateWithAdditionalPurchase(PromotionCalculationData dto, PromotionRequest request, int unmetPromotionQuantity) {
        int updatedFinalQuantity = dto.getFinalQuantity() + unmetPromotionQuantity;
        int freeItems = (updatedFinalQuantity / dto.getBuyQuantity()) * dto.getFreeQuantity();

        PromotionCalculationData updatedDto = dto.withUpdatedQuantities(updatedFinalQuantity, freeItems);
        PromotionStockManager.addProducts(updatedDto, request);
        return updatedDto;
    }
}
