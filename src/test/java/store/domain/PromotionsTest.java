package store.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import store.domain.vo.PromotionName;
import store.domain.vo.RequiredPurchaseQuantity;
import store.domain.vo.FreeQuantity;
import store.domain.vo.PromotionPeriod;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@DisplayName("Promotions 클래스 테스트")
class PromotionsTest {

    @Nested
    @DisplayName("성공 케이스")
    class SuccessCases {

        @Test
        @DisplayName("활성 프로모션을 성공적으로 찾는다")
        void findActivePromotionSuccessfully() {
            Promotion activePromotion = Promotion.of(
                    PromotionName.from("탄산2+1"),
                    RequiredPurchaseQuantity.from(2),
                    FreeQuantity.from(1),
                    PromotionPeriod.of(
                            LocalDate.of(2024, 1, 1),  // 2024년 1월 1일
                            LocalDate.of(2024, 12, 31)  // 2024년 12월 31일
                    )
            );

            Promotions promotions = new Promotions(List.of(activePromotion));

            Optional<Promotion> result = promotions.findActivePromotion("탄산2+1", LocalDate.of(2024, 6, 15));

            assertThat(result).isPresent();
            assertThat(result.get()).isEqualTo(activePromotion);
        }

        @Test
        @DisplayName("비활성 프로모션을 찾지 않는다")
        void findInactivePromotion() {
            Promotion inactivePromotion = Promotion.of(
                    PromotionName.from("탄산2+1"),
                    RequiredPurchaseQuantity.from(2),
                    FreeQuantity.from(1),
                    PromotionPeriod.of(
                            LocalDate.of(2024, 1, 1),
                            LocalDate.of(2024, 6, 30)
                    )
            );

            Promotions promotions = new Promotions(List.of(inactivePromotion));

            Optional<Promotion> result = promotions.findActivePromotion("탄산2+1", LocalDate.of(2024, 7, 1));

            assertThat(result).isNotPresent();
        }
    }

    @Nested
    @DisplayName("실패 케이스")
    class FailureCases {

        @Test
        @DisplayName("주어진 이름의 프로모션이 없는 경우")
        void noPromotionWithGivenName() {
            Promotion promotion = Promotion.of(
                    PromotionName.from("MD추천상품"),
                    RequiredPurchaseQuantity.from(1),
                    FreeQuantity.from(1),
                    PromotionPeriod.of(
                            LocalDate.of(2024, 1, 1),
                            LocalDate.of(2024, 12, 31)
                    )
            );

            Promotions promotions = new Promotions(List.of(promotion));

            Optional<Promotion> result = promotions.findActivePromotion("탄산2+1", LocalDate.of(2024, 6, 15));

            assertThat(result).isNotPresent();
        }

        @Test
        @DisplayName("날짜가 프로모션 기간을 벗어난 경우")
        void promotionOutsideOfPeriod() {
            Promotion promotion = Promotion.of(
                    PromotionName.from("탄산2+1"),
                    RequiredPurchaseQuantity.from(2),
                    FreeQuantity.from(1),
                    PromotionPeriod.of(
                            LocalDate.of(2024, 1, 1),
                            LocalDate.of(2024, 6, 30)
                    )
            );

            Promotions promotions = new Promotions(List.of(promotion));

            Optional<Promotion> result = promotions.findActivePromotion("탄산2+1", LocalDate.of(2024, 7, 1));

            assertThat(result).isNotPresent();
        }
    }
}
