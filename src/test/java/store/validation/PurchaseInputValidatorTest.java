package store.validation;

import static org.assertj.core.api.Assertions.*;
import static store.exception.ErrorMessage.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PurchaseInputValidatorTest {

    @Nested
    @DisplayName("성공 케이스")
    class SuccessCases {

        @ParameterizedTest
        @DisplayName("유효한 입력값이 들어오면 예외가 발생하지 않는다.")
        @ValueSource(strings = {
                "[콜라-2],[사이다-3],[스프라이트-4]",
                "[물-1],[쉐이크-10]",
                "[우유-5],[커피-2],[주스-3]",
                "[빵-6]",
                "[아보카도-3],[아스파라거스-2],[포켓몬빵-1]"
        })
        void shouldNotThrowExceptionForValidInput(String validInput) {
            assertThatNoException().isThrownBy(() -> PurchaseInputValidator.validate(validInput));
        }
    }

    @Nested
    @DisplayName("실패 케이스")
    class FailureCases {

        @Test
        @DisplayName("null 입력값이 들어오면 예외가 발생한다.")
        void shouldThrowExceptionWhenInputIsNull() {
            assertThatThrownBy(() -> PurchaseInputValidator.validate(null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(PURCHASE_INPUT_CANNOT_BE_NULL.getMessage());
        }

        @Test
        @DisplayName("빈 입력값이 들어오면 예외가 발생한다.")
        void shouldThrowExceptionWhenInputIsEmpty() {
            assertThatThrownBy(() -> PurchaseInputValidator.validate(""))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(PURCHASE_INPUT_CANNOT_BE_EMPTY.getMessage());
        }

        @Test
        @DisplayName("대괄호로 묶이지 않은 상품이 있으면 예외가 발생한다.")
        void shouldThrowExceptionForMissingBrackets() {
            String inputWithoutBrackets = "콜라-2, 사이다-3";
            assertThatThrownBy(() -> PurchaseInputValidator.validate(inputWithoutBrackets))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ITEM_MUST_BE_IN_BRACKETS.getMessage());
        }

        @Test
        @DisplayName("상품명과 수량 사이에 하이픈(-)이 없으면 예외가 발생한다.")
        void shouldThrowExceptionForMissingHyphen() {
            String inputWithoutHyphen = "[콜라2],[사이다3]";
            assertThatThrownBy(() -> PurchaseInputValidator.validate(inputWithoutHyphen))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ITEM_MUST_CONTAIN_NAME_AND_QUANTITY.getMessage());
        }

        @Test
        @DisplayName("상품명이 비어 있으면 예외가 발생한다.")
        void shouldThrowExceptionWhenNameIsEmpty() {
            String inputWithEmptyName = "[-2]";

            assertThatThrownBy(() -> PurchaseInputValidator.validate(inputWithEmptyName))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(PRODUCT_NAME_CANNOT_BE_EMPTY.getMessage());
        }

        @Test
        @DisplayName("수량이 비어 있으면 예외가 발생한다.")
        void shouldThrowExceptionWhenQuantityIsEmpty() {
            String inputWithEmptyQuantity = "[콜라-]";

            assertThatThrownBy(() -> PurchaseInputValidator.validate(inputWithEmptyQuantity))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(QUANTITY_CANNOT_BE_EMPTY.getMessage());
        }


        @Test
        @DisplayName("수량이 숫자가 아니면 예외가 발생한다.")
        void shouldThrowExceptionWhenQuantityIsNotNumeric() {
            String inputWithNonNumericQuantity = "[콜라-abc]";
            assertThatThrownBy(() -> PurchaseInputValidator.validate(inputWithNonNumericQuantity))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(QUANTITY_MUST_BE_NUMERIC.getMessage());
        }

        @Test
        @DisplayName("수량이 0일 때 예외가 발생한다.")
        void shouldThrowExceptionWhenQuantityIsZero() {
            String inputWithZeroQuantity = "[콜라-0]";

            assertThatThrownBy(() -> PurchaseInputValidator.validate(inputWithZeroQuantity))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(QUANTITY_MUST_BE_POSITIVE.getMessage());
        }

        @Test
        @DisplayName("수량이 음수일 때 예외가 발생한다.")
        void shouldThrowExceptionWhenQuantityIsNegative() {
            String inputWithNegativeQuantity = "[사이다--3]";

            assertThatThrownBy(() -> PurchaseInputValidator.validate(inputWithNegativeQuantity))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ITEM_MUST_CONTAIN_NAME_AND_QUANTITY.getMessage());
        }

        @Test
        @DisplayName("쉼표로 시작하는 입력값이 들어오면 예외가 발생한다.")
        void shouldThrowExceptionWhenInputStartsWithComma() {
            String inputStartsWithComma = ",[콜라-2],[사이다-3]";
            assertThatThrownBy(() -> PurchaseInputValidator.validate(inputStartsWithComma))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(INPUT_CANNOT_START_OR_END_WITH_COMMA.getMessage());
        }

        @Test
        @DisplayName("쉼표로 끝나는 입력값이 들어오면 예외가 발생한다.")
        void shouldThrowExceptionWhenInputEndsWithComma() {
            String inputEndsWithComma = "[콜라-2],[사이다-3],";
            assertThatThrownBy(() -> PurchaseInputValidator.validate(inputEndsWithComma))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(INPUT_CANNOT_START_OR_END_WITH_COMMA.getMessage());
        }

        @Test
        @DisplayName("중간에 빈 항목이 포함된 경우 예외가 발생한다.")
        void shouldThrowExceptionWhenEmptyItemInBetween() {
            String inputWithEmptyItem = "[콜라-2],,[사이다-3]";

            assertThatThrownBy(() -> PurchaseInputValidator.validate(inputWithEmptyItem))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(EMPTY_ITEM_INCLUDED.getMessage());
        }
    }
}
