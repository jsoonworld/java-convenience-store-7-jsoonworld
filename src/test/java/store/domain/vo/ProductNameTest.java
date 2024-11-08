package store.domain.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import store.exception.ErrorMessage;

@DisplayName("ProductName 클래스 테스트")
class ProductNameTest {

    @Nested
    @DisplayName("성공 케이스")
    class SuccessCases {

        @Test
        @DisplayName("유효한 이름으로 ProductName 객체를 생성할 수 있다")
        void createProductNameWithValidName() {
            ProductName productName = ProductName.from("ValidProduct");

            assertThat(productName).isNotNull();
            assertThat(productName.getName()).isEqualTo("ValidProduct");
        }

        @Test
        @DisplayName("같은 이름을 가진 두 ProductName 객체는 동일하다")
        void productNameEquality() {
            ProductName name1 = ProductName.from("SameProduct");
            ProductName name2 = ProductName.from("SameProduct");

            assertThat(name1).isEqualTo(name2);
            assertThat(name1.hashCode()).isEqualTo(name2.hashCode());
        }
    }

    @Nested
    @DisplayName("실패 케이스")
    class FailureCases {

        @Test
        @DisplayName("null 이름으로 생성 시 예외 발생")
        void throwExceptionWhenNameIsNull() {
            assertThatThrownBy(() -> ProductName.from(null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ErrorMessage.NULL_PRODUCT_NAME.getMessage());
        }

        @Test
        @DisplayName("빈 문자열 이름으로 생성 시 예외 발생")
        void throwExceptionWhenNameIsBlank() {
            assertThatThrownBy(() -> ProductName.from(" "))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ErrorMessage.EMPTY_PRODUCT_NAME.getMessage());
        }
    }
}
