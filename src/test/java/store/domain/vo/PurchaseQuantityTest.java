package store.domain.vo;

import static org.assertj.core.api.Assertions.*;
import static store.exception.ErrorMessage.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PurchaseQuantityTest {

    @Nested
    @DisplayName("성공 케이스")
    class SuccessCases {

        @Test
        @DisplayName("유효한 수량으로 객체를 생성한다.")
        void createPurchaseQuantitySuccessfully() {
            PurchaseQuantity quantity = PurchaseQuantity.of(5);
            assertThat(quantity.getAmount()).isEqualTo(5);
        }
    }

    @Nested
    @DisplayName("실패 케이스")
    class FailureCases {

        @Test
        @DisplayName("수량이 0일 경우 예외가 발생한다.")
        void throwExceptionWhenQuantityIsZero() {
            assertThatThrownBy(() -> PurchaseQuantity.of(0))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(QUANTITY_MUST_BE_POSITIVE.getMessage());
        }

        @Test
        @DisplayName("수량이 음수일 경우 예외가 발생한다.")
        void throwExceptionWhenQuantityIsNegative() {
            assertThatThrownBy(() -> PurchaseQuantity.of(-3))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(QUANTITY_MUST_BE_POSITIVE.getMessage());
        }
    }
}
