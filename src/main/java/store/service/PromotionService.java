package store.service;

import store.domain.Promotion;
import store.repository.PromotionRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class PromotionService {
    private final PromotionRepository promotionRepository;

    public PromotionService(PromotionRepository promotionRepository) {
        this.promotionRepository = promotionRepository;
    }

    // 유효한 단일 프로모션을 조회하며 중복 프로모션이 있을 경우 경고 메시지를 출력
    public Optional<Promotion> getValidPromotionForProduct(String promotionName) {
        if (promotionName == null || promotionName.equalsIgnoreCase("null")) {
            return Optional.empty();
        }

        LocalDate today = LocalDate.now();

        // 프로모션 이름이 일치하고 오늘 날짜에 유효한 프로모션 리스트 가져오기
        List<Promotion> validPromotions = promotionRepository.findPromotionsByName(promotionName)
                .stream()
                .filter(promo -> promo.isActiveOn(today))
                .toList();

        // 유효한 프로모션이 여러 개일 경우 중복 경고 메시지를 출력
        if (validPromotions.size() > 1) {
            System.out.println("[ERROR] 중복 프로모션이 감지되었습니다.");
            return Optional.empty();
        }

        // 유효한 프로모션이 하나인 경우 해당 프로모션 반환
        return validPromotions.isEmpty() ? Optional.empty() : Optional.of(validPromotions.get(0));
    }

    public List<Promotion> findPromotionsByName(String promotionName) {
        return promotionRepository.findPromotionsByName(promotionName);
    }
}
