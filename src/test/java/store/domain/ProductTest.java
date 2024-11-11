package store.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import store.domain.vo.ProductName;
import store.domain.vo.Price;
import store.domain.vo.PromotionName;
import store.domain.vo.Quantity;

@DisplayName("Product 클래스 테스트")
class ProductTest {

    @Nested
    @DisplayName("성공 케이스")
    class SuccessCases {

        @Test
        @DisplayName("유효한 값으로 Product 객체를 생성할 수 있다.")
        void createProductWithValidValues() {
            Product product = Product.of(
                    ProductName.from("콜라"),
                    Price.from(1000),
                    Quantity.from(10),
                    PromotionName.from("탄산2+1")
            );

            assertThat(product).isNotNull();
            assertThat(product.getName()).isEqualTo("콜라");
            assertThat(product.getPriceValue()).isEqualTo(1000);
            assertThat(product.getQuantityValue()).isEqualTo(10);
            assertThat(product.isPromotional()).isTrue();
        }

        @Test
        @DisplayName("PromotionName이 'null'이면 hasPromotion이 false를 반환한다")
        void productWithoutPromotion() {
            Product product = Product.of(
                    ProductName.from("물"),
                    Price.from(500),
                    Quantity.from(10),
                    PromotionName.from("null")
            );

            assertThat(product.isPromotional()).isFalse();
        }

        @Test
        @DisplayName("재고가 0인 경우 hasNoStock이 true를 반환한다")
        void productOutOfStock() {
            Product product = Product.of(
                    ProductName.from("탄산수"),
                    Price.from(1200),
                    Quantity.from(0),
                    PromotionName.from("탄산2+1")
            );

            assertThat(product.hasNoStock()).isTrue();
        }
    }
}
