package store.domain.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static store.exception.ErrorMessage.NEGATIVE_PROMOTION_QUANTITY;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("RequiredPurchaseQuantity 클래스 테스트")
class RequiredPurchaseQuantityTest {

    @Nested
    @DisplayName("성공 케이스")
    class SuccessCases {

        @Test
        @DisplayName("유효한 수량으로 RequiredPurchaseQuantity 객체를 생성할 수 있다")
        void createWithValidQuantity() {
            RequiredPurchaseQuantity quantity = RequiredPurchaseQuantity.from(5);

            assertThat(quantity).isNotNull();
            assertThat(quantity.getValue()).isEqualTo(5);
        }

        @Test
        @DisplayName("수량이 0인 경우에도 생성 가능하다")
        void createWithZeroQuantity() {
            RequiredPurchaseQuantity quantity = RequiredPurchaseQuantity.from(0);

            assertThat(quantity).isNotNull();
            assertThat(quantity.getValue()).isEqualTo(0);
        }
    }

    @Nested
    @DisplayName("실패 케이스")
    class FailureCases {

        @Test
        @DisplayName("음수 수량으로 객체 생성 시 예외 발생")
        void throwExceptionWhenQuantityIsNegative() {
            assertThatThrownBy(() -> RequiredPurchaseQuantity.from(-1))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(NEGATIVE_PROMOTION_QUANTITY.getMessage());
        }
    }
}
