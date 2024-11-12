package store.repository;

import store.domain.promotion.Promotion;

import java.util.List;
import java.util.stream.Collectors;

public class PromotionRepository {
    private final List<Promotion> promotions;

    public PromotionRepository(List<Promotion> promotions) {
        this.promotions = promotions;
    }

    public List<Promotion> findPromotionsByName(String promotionName) {
        return promotions.stream()
                .filter(promo -> promo.getName().equalsIgnoreCase(promotionName))
                .collect(Collectors.toList());
    }
}
