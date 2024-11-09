package store.repository;

import store.domain.Promotion;

import java.util.List;
import java.util.Optional;

public class PromotionRepository {
    private final List<Promotion> promotions;

    public PromotionRepository(List<Promotion> promotions) {
        this.promotions = promotions;
    }

    public Optional<Promotion> findPromotionByName(String promotionName) {
        return promotions.stream()
                .filter(promo -> promo.getName().equalsIgnoreCase(promotionName))
                .findFirst();
    }

    public List<Promotion> getAllPromotions() {
        return promotions;
    }
}