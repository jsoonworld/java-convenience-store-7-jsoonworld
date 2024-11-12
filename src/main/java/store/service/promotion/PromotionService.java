package store.service.promotion;

import static store.exception.ErrorMessage.*;

import camp.nextstep.edu.missionutils.DateTimes;
import store.domain.promotion.Promotion;
import store.repository.PromotionRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class PromotionService {
    private final PromotionRepository promotionRepository;

    private static final String NULL_PROMOTION_NAME = "null";
    public static final int MAX_DUPLICATE_PROMOTIONS = 1;

    public PromotionService(PromotionRepository promotionRepository) {
        this.promotionRepository = promotionRepository;
    }

    public Optional<Promotion> getValidPromotionForProduct(String promotionName) {
        return Optional.ofNullable(promotionName)
                .filter(this::isPromotion)
                .map(this::findValidPromotions)
                .filter(this::hasNoDuplicatePromotions)
                .flatMap(this::getSingleValidPromotion);
    }

    private boolean isPromotion(String promotionName) {
        return !promotionName.equalsIgnoreCase(NULL_PROMOTION_NAME);
    }

    private List<Promotion> findValidPromotions(String promotionName) {
        LocalDate today = LocalDate.from(DateTimes.now());
        return promotionRepository.findPromotionsByName(promotionName)
                .stream()
                .filter(promo -> promo.isActiveOn(today))
                .toList();
    }

    private boolean hasNoDuplicatePromotions(List<Promotion> validPromotions) {
        if (validPromotions.size() > MAX_DUPLICATE_PROMOTIONS) {
            throw new IllegalArgumentException(DUPLICATE_PROMOTION.getMessage());
        }
        return true;
    }

    private Optional<Promotion> getSingleValidPromotion(List<Promotion> validPromotions) {
        return validPromotions.stream().findFirst();
    }
}
