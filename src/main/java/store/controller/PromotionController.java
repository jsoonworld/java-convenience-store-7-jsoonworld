package store.controller;

import store.domain.Promotion;
import store.service.PromotionService;

import java.util.Optional;

public class PromotionController {
    private final PromotionService promotionService;

    public PromotionController(PromotionService promotionService) {
        this.promotionService = promotionService;
    }

    public Optional<Promotion> getActivePromotion(String promotionName) {
        return promotionService.getValidPromotionForProduct(promotionName);
    }
}
