package store.repository;

import store.domain.Promotion;

import java.util.List;
import java.util.stream.Collectors;

public class PromotionRepository {
    private final List<Promotion> promotions;

    public PromotionRepository(List<Promotion> promotions) {
        this.promotions = promotions;
    }

    // 특정 이름의 모든 프로모션을 가져오기
    public List<Promotion> findPromotionsByName(String promotionName) {
        return promotions.stream()
                .filter(promo -> promo.getName().equalsIgnoreCase(promotionName))
                .collect(Collectors.toList());
    }

    // 모든 프로모션 반환
    public List<Promotion> getAllPromotions() {
        return promotions;
    }
}
