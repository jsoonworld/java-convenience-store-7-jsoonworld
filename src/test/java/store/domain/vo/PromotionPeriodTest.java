package store.domain.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import store.exception.ErrorMessage;

import java.time.LocalDate;

@DisplayName("PromotionPeriod 클래스 테스트")
class PromotionPeriodTest {

    @Nested
    @DisplayName("성공 케이스")
    class SuccessCases {

        @Test
        @DisplayName("유효한 시작일과 종료일로 PromotionPeriod 객체를 생성할 수 있다")
        void createPromotionPeriodWithValidDates() {
            LocalDate startDate = LocalDate.of(2024, 1, 1);
            LocalDate endDate = LocalDate.of(2024, 12, 31);

            PromotionPeriod period = PromotionPeriod.of(startDate, endDate);

            assertThat(period).isNotNull();
            assertThat(period.isWithinPeriod(LocalDate.of(2024, 6, 15))).isTrue();
        }

        @Test
        @DisplayName("프로모션 날짜가 시작일과 종료일 사이에 있을 경우 isWithinPeriod가 true를 반환한다")
        void dateWithinPeriodReturnsTrue() {
            LocalDate startDate = LocalDate.of(2024, 1, 1);
            LocalDate endDate = LocalDate.of(2024, 12, 31);
            PromotionPeriod period = PromotionPeriod.of(startDate, endDate);

            LocalDate dateWithin = LocalDate.of(2024, 6, 15);

            assertThat(period.isWithinPeriod(dateWithin)).isTrue();
        }

        @Test
        @DisplayName("프로모션 날짜가 시작일과 같을 경우 isWithinPeriod가 true를 반환한다")
        void dateEqualsStartDateReturnsTrue() {
            LocalDate startDate = LocalDate.of(2024, 1, 1);
            PromotionPeriod period = PromotionPeriod.of(startDate, LocalDate.of(2024, 12, 31));

            assertThat(period.isWithinPeriod(startDate)).isTrue();
        }

        @Test
        @DisplayName("프로모션 날짜가 종료일과 같을 경우 isWithinPeriod가 true를 반환한다")
        void dateEqualsEndDateReturnsTrue() {
            LocalDate endDate = LocalDate.of(2024, 12, 31);
            PromotionPeriod period = PromotionPeriod.of(LocalDate.of(2024, 1, 1), endDate);

            assertThat(period.isWithinPeriod(endDate)).isTrue();
        }
    }

    @Nested
    @DisplayName("실패 케이스")
    class FailureCases {

        @Test
        @DisplayName("시작일이 null인 경우 예외 발생")
        void throwExceptionWhenStartDateIsNull() {
            LocalDate endDate = LocalDate.of(2024, 12, 31);

            assertThatThrownBy(() -> PromotionPeriod.of(null, endDate))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ErrorMessage.NULL_START_DATE.getMessage());
        }

        @Test
        @DisplayName("종료일이 null인 경우 예외 발생")
        void throwExceptionWhenEndDateIsNull() {
            LocalDate startDate = LocalDate.of(2024, 1, 1);

            assertThatThrownBy(() -> PromotionPeriod.of(startDate, null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ErrorMessage.NULL_END_DATE.getMessage());
        }

        @Test
        @DisplayName("시작일이 종료일 이후인 경우 예외 발생")
        void throwExceptionWhenStartDateIsAfterEndDate() {
            LocalDate startDate = LocalDate.of(2024, 12, 31);
            LocalDate endDate = LocalDate.of(2024, 1, 1);

            assertThatThrownBy(() -> PromotionPeriod.of(startDate, endDate))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ErrorMessage.INVALID_PROMOTION_PERIOD.getMessage());
        }
    }
}
