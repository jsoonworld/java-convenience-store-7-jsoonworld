package store.domain.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import store.exception.ErrorMessage;

@DisplayName("FreeQuantity 클래스 테스트")
class FreeQuantityTest {

    @Nested
    @DisplayName("성공 케이스")
    class SuccessCases {

        @Test
        @DisplayName("0 이상의 유효한 값으로 FreeQuantity 객체를 생성할 수 있다")
        void createFreeQuantityWithValidValue() {
            FreeQuantity freeQuantity = FreeQuantity.from(1);

            assertThat(freeQuantity).isNotNull();
            assertThat(freeQuantity.value()).isEqualTo(1);
        }

        @Test
        @DisplayName("FreeQuantity 값이 0일 때도 객체를 생성할 수 있다")
        void createFreeQuantityWithZeroValue() {
            FreeQuantity freeQuantity = FreeQuantity.from(0);

            assertThat(freeQuantity).isNotNull();
            assertThat(freeQuantity.value()).isEqualTo(0);
        }
    }

    @Nested
    @DisplayName("실패 케이스")
    class FailureCases {

        @Test
        @DisplayName("음수 값으로 FreeQuantity 생성 시 예외 발생")
        void throwExceptionWhenNegativeValue() {
            assertThatThrownBy(() -> FreeQuantity.from(-1))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ErrorMessage.NEGATIVE_FREE_QUANTITY.getMessage());
        }
    }
}
