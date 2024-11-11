package store.controller;

import store.domain.Product;
import store.dto.request.promotion.PromotionRequest;
import store.dto.response.promotion.PromotionResponse;
import store.dto.response.promotion.PromotionResult;
import store.service.promotion.PromotionHandler;

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
