package store.domain.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import store.exception.ErrorMessage;

@DisplayName("Price 클래스 테스트")
class PriceTest {

    @Nested
    @DisplayName("성공 케이스")
    class SuccessCases {

        @Test
        @DisplayName("정상적인 가격으로 Price 생성")
        void shouldCreatePriceWithValidValue() {
            Price price = Price.from(1000);
            assertThat(price.getValue()).isEqualTo(1000);
        }

        @Test
        @DisplayName("같은 가격의 Price 객체가 동등한지 확인")
        void shouldReturnCorrectEqualityForSameValue() {
            Price price1 = Price.from(1000);
            Price price2 = Price.from(1000);
            assertThat(price1).isEqualTo(price2);
        }

        @Test
        @DisplayName("같은 가격의 Price 객체가 같은 해시 코드를 가지는지 확인")
        void shouldReturnCorrectHashCode() {
            Price price1 = Price.from(1000);
            Price price2 = Price.from(1000);
            assertThat(price1.hashCode()).isEqualTo(price2.hashCode());
        }
    }

    @Nested
    @DisplayName("실패 케이스")
    class FailureCases {

        @Test
        @DisplayName("가격이 null일 때 예외 발생")
        void shouldThrowExceptionForNullValue() {
            assertThatThrownBy(() -> Price.from(null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ErrorMessage.NULL_PRICE.getMessage());
        }

        @Test
        @DisplayName("가격이 음수일 때 예외 발생")
        void shouldThrowExceptionForNegativeValue() {
            assertThatThrownBy(() -> Price.from(-1))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ErrorMessage.NEGATIVE_PRICE.getMessage());
        }

        @Test
        @DisplayName("다른 가격의 Price 객체가 동등하지 않은지 확인")
        void shouldReturnCorrectInequalityForDifferentValues() {
            Price price1 = Price.from(1000);
            Price price2 = Price.from(500);
            assertThat(price1).isNotEqualTo(price2);
        }
    }
}
