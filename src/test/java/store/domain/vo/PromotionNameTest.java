package store.domain.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import store.exception.ErrorMessage;

@DisplayName("PromotionName 클래스 테스트")
class PromotionNameTest {

    @Nested
    @DisplayName("성공 케이스")
    class SuccessCases {

        @Test
        @DisplayName("유효한 프로모션 이름으로 PromotionName 객체를 생성할 수 있다")
        void createPromotionNameWithValidValue() {
            PromotionName promotionName = PromotionName.from("사장님이 미쳤어요");

            assertThat(promotionName).isNotNull();
            assertThat(promotionName.value()).isEqualTo("사장님이 미쳤어요");
        }

        @Test
        @DisplayName("같은 이름을 가진 두 PromotionName 객체는 동일하다")
        void promotionNameEquality() {
            PromotionName name1 = PromotionName.from("알바가 미쳤어요");
            PromotionName name2 = PromotionName.from("알바가 미쳤어요");

            assertThat(name1).isEqualTo(name2);
            assertThat(name1.hashCode()).isEqualTo(name2.hashCode());
        }

        @Test
        @DisplayName("\"null\" 이름을 가진 PromotionName 객체는 isNullPromotion이 true를 반환한다")
        void promotionNameIsNullPromotion() {
            PromotionName promotionName = PromotionName.from("null");

            assertThat(promotionName.isNullPromotion()).isTrue();
        }
    }

    @Nested
    @DisplayName("실패 케이스")
    class FailureCases {

        @Test
        @DisplayName("null 값으로 PromotionName 객체 생성 시 예외 발생")
        void throwExceptionWhenValueIsNull() {
            assertThatThrownBy(() -> PromotionName.from(null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ErrorMessage.INVALID_PROMOTION_NAME.getMessage());
        }
    }
}
