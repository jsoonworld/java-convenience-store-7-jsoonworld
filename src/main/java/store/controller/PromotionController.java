package store.controller;

import store.domain.Product;
import store.dto.request.PromotionRequest;
import store.dto.response.PromotionResponse;
import store.dto.response.PromotionResult;
import store.service.PromotionHandler;

import java.util.List;

public class PromotionController {

    private final PromotionHandler promotionHandler;

    public PromotionController(PromotionHandler promotionHandler) {
        this.promotionHandler = promotionHandler;
    }

    public PromotionResult handlePromotion(PromotionRequest promotionRequest) {
        PromotionResponse promotionResponse = promotionHandler.applyPromotion(promotionRequest);

        int remainingQuantity = promotionResponse.getFinalQuantity();
        int totalPromotionalDiscount = promotionResponse.getDiscountAmount();
        List<Product> productsToSave = promotionResponse.getProductsToSave();
        StringBuilder promotionalDetails = promotionResponse.getPromotionalDetails();

        return PromotionResult.of(remainingQuantity, totalPromotionalDiscount, productsToSave, promotionalDetails);
    }
}
