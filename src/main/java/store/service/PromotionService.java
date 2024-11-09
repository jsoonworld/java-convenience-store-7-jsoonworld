package store.service;

import store.domain.Promotion;
import store.repository.PromotionRepository;

import camp.nextstep.edu.missionutils.DateTimes;

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
        LocalDate today = DateTimes.now().toLocalDate();
        return promotionRepository.findPromotionByName(promotionName)
                .filter(promo -> promo.isActiveOn(today));
    }
}
