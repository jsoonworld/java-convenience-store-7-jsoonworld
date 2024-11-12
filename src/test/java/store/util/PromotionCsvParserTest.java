package store.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static store.exception.ErrorMessage.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import store.domain.promotion.Promotion;
import store.util.parser.PromotionCsvParser;

import java.time.LocalDate;
import java.util.List;

@DisplayName("PromotionCsvParser 클래스 테스트")
class PromotionCsvParserTest {

    private final PromotionCsvParser parser = new PromotionCsvParser();

    @Nested
    @DisplayName("성공 케이스")
    class SuccessCases {

        @Test
        @DisplayName("유효한 데이터로 프로모션 목록 파싱")
        void parsePromotions_withValidData() {
            List<String> lines = List.of(
                    "name,buy,get,start_date,end_date",
                    "탄산2+1,2,1,2024-01-01,2024-12-31",
                    "MD추천상품,1,1,2024-01-01,2024-12-31"
            );

            List<Promotion> promotions = parser.parsePromotions(lines);

            assertThat(promotions).hasSize(2);

            Promotion firstPromotion = promotions.get(0);
            assertThat(firstPromotion.getName()).isEqualTo("탄산2+1");
            assertThat(firstPromotion.getBuyQuantity()).isEqualTo(2);
            assertThat(firstPromotion.getFreeQuantity()).isEqualTo(1);
            assertThat(firstPromotion.isActiveOn(LocalDate.of(2024, 1, 1))).isTrue();
            assertThat(firstPromotion.isActiveOn(LocalDate.of(2024, 12, 31))).isTrue();
        }

        @Test
        @DisplayName("유효한 단일 라인으로 프로모션 파싱")
        void parsePromotion_withValidSingleLine() {
            String line = "탄산2+1,2,1,2024-01-01,2024-12-31";

            Promotion promotion = parser.parsePromotions(List.of("header", line)).get(0);

            assertThat(promotion.getName()).isEqualTo("탄산2+1");
            assertThat(promotion.getBuyQuantity()).isEqualTo(2);
            assertThat(promotion.getFreeQuantity()).isEqualTo(1);
            assertThat(promotion.isActiveOn(LocalDate.of(2024, 1, 1))).isTrue();
            assertThat(promotion.isActiveOn(LocalDate.of(2024, 12, 31))).isTrue();
            assertThat(promotion.isActiveOn(LocalDate.of(2025, 1, 1))).isFalse();
        }
    }

    @Nested
    @DisplayName("실패 케이스")
    class FailureCases {

        @Test
        @DisplayName("필드 수가 부족하여 프로모션 파싱에 실패")
        void parsePromotion_withMissingField() {
            String line = "탄산2+1,2,1,2024-01-01";
            assertThatThrownBy(() -> parser.parsePromotions(List.of("header", line)))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(INVALID_PROMOTION_FORMAT.getMessage());
        }

        @Test
        @DisplayName("필드 수가 초과되어 프로모션 파싱에 실패")
        void parsePromotion_withExtraField() {
            String line = "탄산2+1,2,1,2024-01-01,2024-12-31,추가필드";
            assertThatThrownBy(() -> parser.parsePromotions(List.of("header", line)))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(INVALID_PROMOTION_FORMAT.getMessage());
        }

        @Test
        @DisplayName("잘못된 날짜 형식으로 프로모션 파싱에 실패")
        void parsePromotion_withInvalidDateFormat() {
            String line = "탄산2+1,2,1,2024-01-01,잘못된날짜";
            assertThatThrownBy(() -> parser.parsePromotions(List.of("header", line)))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(INVALID_DATE_FORMAT.getMessage());
        }

        @Test
        @DisplayName("정수가 아닌 구매 수량으로 프로모션 파싱에 실패")
        void parsePromotion_withNonIntegerBuyQuantity() {
            String line = "탄산2+1,두개,1,2024-01-01,2024-12-31";
            assertThatThrownBy(() -> parser.parsePromotions(List.of("header", line)))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(INVALID_INTEGER_FORMAT.getMessage());
        }

        @Test
        @DisplayName("정수가 아닌 무료 수량으로 프로모션 파싱에 실패")
        void parsePromotion_withNonIntegerFreeQuantity() {
            String line = "탄산2+1,2,하나,2024-01-01,2024-12-31";
            assertThatThrownBy(() -> parser.parsePromotions(List.of("header", line)))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(INVALID_INTEGER_FORMAT.getMessage());
        }
    }
}
