package store.domain.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import store.exception.ErrorMessage;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Quantity 클래스 테스트")
class QuantityTest {

    @Nested
    @DisplayName("성공 케이스")
    class SuccessCases {

        @Test
        @DisplayName("정상적인 양으로 Quantity 생성")
        void shouldCreateQuantityWithValidAmount() {
            Quantity quantity = Quantity.from(10);
            assertThat(quantity.getAmount()).isEqualTo(10);
        }

        @Test
        @DisplayName("재고가 없는 경우 확인 (수량이 0일 때)")
        void shouldIdentifyOutOfStockWhenAmountIsZero() {
            Quantity quantity = Quantity.from(0);
            assertThat(quantity.isOutOfStock()).isTrue();
        }

        @Test
        @DisplayName("재고가 있는 경우 확인 (수량이 0이 아닐 때)")
        void shouldNotIdentifyOutOfStockWhenAmountIsNonZero() {
            Quantity quantity = Quantity.from(5);
            assertThat(quantity.isOutOfStock()).isFalse();
        }

        @Test
        @DisplayName("같은 수량의 Quantity 객체가 동등한지 확인")
        void shouldReturnCorrectEqualityForSameAmount() {
            Quantity quantity1 = Quantity.from(10);
            Quantity quantity2 = Quantity.from(10);
            assertThat(quantity1).isEqualTo(quantity2);
        }

        @Test
        @DisplayName("같은 수량의 Quantity 객체가 같은 해시 코드를 가지는지 확인")
        void shouldReturnCorrectHashCode() {
            Quantity quantity1 = Quantity.from(10);
            Quantity quantity2 = Quantity.from(10);
            assertThat(quantity1.hashCode()).isEqualTo(quantity2.hashCode());
        }
    }

    @Nested
    @DisplayName("실패 케이스")
    class FailureCases {

        @Test
        @DisplayName("수량이 null일 때 예외 발생")
        void shouldThrowExceptionForNullAmount() {
            assertThatThrownBy(() -> Quantity.from(null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ErrorMessage.NULL_QUANTITY.getMessage());
        }

        @Test
        @DisplayName("수량이 음수일 때 예외 발생")
        void shouldThrowExceptionForNegativeAmount() {
            assertThatThrownBy(() -> Quantity.from(-1))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ErrorMessage.NEGATIVE_QUANTITY.getMessage());
        }

        @Test
        @DisplayName("다른 수량의 Quantity 객체가 동등하지 않은지 확인")
        void shouldReturnCorrectInequalityForDifferentAmounts() {
            Quantity quantity1 = Quantity.from(10);
            Quantity quantity2 = Quantity.from(5);
            assertThat(quantity1).isNotEqualTo(quantity2);
        }
    }
}
