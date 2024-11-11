package store.service;

import store.dto.init.PromotionCalculationData;
import store.dto.request.PromotionRequest;
import store.dto.response.PromotionResponse;
import store.view.InputView;
import store.view.OutputView;

public class PromotionProcessor {

    private final PromotionAdjuster adjuster;

    public PromotionProcessor(InputView inputView, OutputView outputView) {
        this.adjuster = new PromotionAdjuster(inputView, outputView);
    }

    public PromotionResponse applyPromotion(PromotionRequest request) {
        PromotionCalculationData dto = PromotionCalculationData.of(request);

        if (!isPromotionEligible(dto)) {
            return PromotionResponse.emptyResponse(request.getRequestedQuantity());
        }

        // 업데이트된 DTO를 반영하도록 수정
        dto = adjuster.handleAdditionalPurchase(request, dto);

        PromotionStockManager.processRemainingStock(dto, request);
        return PromotionResponse.of(dto.getFinalQuantity(), dto.getDiscountAmount(), dto.getProductsToSave(), dto.getPromotionalDetails());
    }

    private boolean isPromotionEligible(PromotionCalculationData dto) {
        return dto.getBuyQuantity() > 0 && dto.getFreeQuantity() > 0;
    }
}