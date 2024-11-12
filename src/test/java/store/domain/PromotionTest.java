package store.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import store.domain.promotion.Promotion;
import store.domain.vo.PromotionName;
import store.domain.vo.promotion.RequiredPurchaseQuantity;
import store.domain.vo.promotion.FreeQuantity;
import store.domain.vo.promotion.PromotionPeriod;

import java.time.LocalDate;

@DisplayName("Promotion 클래스 테스트")
class PromotionTest {

    @Nested
    @DisplayName("성공 케이스")
    class SuccessCases {

        @Test
        @DisplayName("유효한 값으로 Promotion 객체를 생성할 수 있다")
        void createPromotionWithValidValues() {
            Promotion promotion = Promotion.of(
                    PromotionName.from("탄산2+1"),
                    RequiredPurchaseQuantity.from(2),
                    FreeQuantity.from(1),
                    PromotionPeriod.of(
                            LocalDate.of(2024, 1, 1),  // 2024년 1월 1일
                            LocalDate.of(2024, 12, 31)  // 2024년 12월 31일
                    )
            );

            assertThat(promotion).isNotNull();
            assertThat(promotion.getName()).isEqualTo("탄산2+1");
            assertThat(promotion.getBuyQuantity()).isEqualTo(2);
            assertThat(promotion.getFreeQuantity()).isEqualTo(1);
        }

        @Test
        @DisplayName("지정된 날짜가 프로모션 기간 내에 있을 경우 isActiveOn 메서드가 true를 반환한다")
        void promotionIsActiveWithinPeriod() {
            Promotion promotion = Promotion.of(
                    PromotionName.from("탄산2+1"),
                    RequiredPurchaseQuantity.from(2),
                    FreeQuantity.from(1),
                    PromotionPeriod.of(
                            LocalDate.of(2024, 1, 1),
                            LocalDate.of(2024, 12, 31)
                    )
            );

            LocalDate activeDate = LocalDate.of(2024, 6, 15);
            assertThat(promotion.isActiveOn(activeDate)).isTrue();
        }

        @Test
        @DisplayName("지정된 날짜가 프로모션 기간 밖에 있을 경우 isActiveOn 메서드가 false를 반환한다")
        void promotionIsInactiveOutsidePeriod() {
            Promotion promotion = Promotion.of(
                    PromotionName.from("탄산2+1"),
                    RequiredPurchaseQuantity.from(2),
                    FreeQuantity.from(1),
                    PromotionPeriod.of(
                            LocalDate.of(2024, 1, 1),
                            LocalDate.of(2024, 12, 31)
                    )
            );

            LocalDate inactiveDate = LocalDate.of(2025, 1, 1);
            assertThat(promotion.isActiveOn(inactiveDate)).isFalse();
        }
    }
}
