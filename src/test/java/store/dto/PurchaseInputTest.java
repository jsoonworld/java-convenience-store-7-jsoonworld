package store.dto;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.exception.ErrorMessage;

class PurchaseInputTest {

    @Test
    @DisplayName("null 입력값이 들어오면 예외를 발생시킨다.")
    void shouldThrowExceptionWhenInputIsNull() {
        assertThatThrownBy(() -> PurchaseInput.from(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.PURCHASE_INPUT_CANNOT_BE_NULL.getMessage());
    }

    @Test
    @DisplayName("빈 입력값이 들어오면 예외를 발생시킨다.")
    void shouldThrowExceptionWhenInputIsEmpty() {
        assertThatThrownBy(() -> PurchaseInput.from(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.PURCHASE_INPUT_CANNOT_BE_EMPTY.getMessage());
    }

    @Test
    @DisplayName("공백만 있는 입력값이 들어오면 예외를 발생시킨다.")
    void shouldThrowExceptionWhenInputIsBlank() {
        assertThatThrownBy(() -> PurchaseInput.from("    "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.PURCHASE_INPUT_CANNOT_BE_EMPTY.getMessage());
    }

    @Test
    @DisplayName("유효한 입력값이 들어오면 예외를 발생시키지 않는다.")
    void shouldNotThrowExceptionWhenInputIsValid() {
        PurchaseInput purchaseInput = PurchaseInput.from("[콜라-2]");
        assertThat(purchaseInput.getInput()).isEqualTo("[콜라-2]");
    }
}
