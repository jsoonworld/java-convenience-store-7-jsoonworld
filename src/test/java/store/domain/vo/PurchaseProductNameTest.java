package store.domain.vo;

import static org.assertj.core.api.Assertions.*;
import static store.exception.ErrorMessage.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PurchaseProductNameTest {

    @Nested
    @DisplayName("성공 케이스")
    class SuccessCases {

        @Test
        @DisplayName("유효한 상품명으로 객체를 생성한다.")
        void createPurchaseProductNameSuccessfully() {
            PurchaseProductName productName = PurchaseProductName.of("콜라");
            assertThat(productName.getName()).isEqualTo("콜라");
        }
    }

    @Nested
    @DisplayName("실패 케이스")
    class FailureCases {

        @Test
        @DisplayName("상품명이 null이면 예외가 발생한다.")
        void throwExceptionWhenNameIsNull() {
            assertThatThrownBy(() -> PurchaseProductName.of(null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(PRODUCT_NAME_CANNOT_BE_NULL.getMessage());
        }

        @Test
        @DisplayName("상품명이 빈 문자열이면 예외가 발생한다.")
        void throwExceptionWhenNameIsEmpty() {
            assertThatThrownBy(() -> PurchaseProductName.of(""))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(PRODUCT_NAME_CANNOT_BE_BLANK.getMessage());
        }

        @Test
        @DisplayName("상품명이 공백 문자열이면 예외가 발생한다.")
        void throwExceptionWhenNameIsBlank() {
            assertThatThrownBy(() -> PurchaseProductName.of("   "))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(PRODUCT_NAME_CANNOT_BE_BLANK.getMessage());
        }
    }
}
