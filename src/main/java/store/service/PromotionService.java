package store.service;

import camp.nextstep.edu.missionutils.DateTimes;
import store.domain.Promotion;
import store.repository.PromotionRepository;

import java.time.LocalDate;
import java.util.Optional;

public class PromotionService {
    private final PromotionRepository promotionRepository;

    public PromotionService(PromotionRepository promotionRepository) {
        this.promotionRepository = promotionRepository;
    }

    public Optional<Promotion> getValidPromotionForProduct(String promotionName) {
        if (promotionName == null || promotionName.equalsIgnoreCase("null")) {
            return Optional.empty();
        }
        LocalDate today = LocalDate.from(DateTimes.now());
        return promotionRepository.findPromotionByName(promotionName)
                .filter(promo -> promo.isActiveOn(today));
    }
}
