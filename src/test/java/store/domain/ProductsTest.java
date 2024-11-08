package store.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import store.domain.vo.Price;
import store.domain.vo.ProductName;
import store.domain.vo.PromotionName;
import store.domain.vo.Quantity;

import java.util.List;

@DisplayName("Products 클래스 테스트")
class ProductsTest {

    @Nested
    @DisplayName("성공 케이스")
    class SuccessCases {

        @Test
        @DisplayName("프로모션이 있는 제품만 필터링하여 반환")
        void findProductsOnPromotion() {
            Product product1 = Product.of(
                    ProductName.from("콜라"),
                    Price.from(1000),
                    Quantity.from(10),
                    PromotionName.from("탄산2+1")
            );

            Product product2 = Product.of(
                    ProductName.from("사이다"),
                    Price.from(1000),
                    Quantity.from(8),
                    PromotionName.from("탄산2+1")
            );

            Product product3 = Product.of(
                    ProductName.from("물"),
                    Price.from(500),
                    Quantity.from(10),
                    PromotionName.from("null")
            );

            Product product4 = Product.of(
                    ProductName.from("컵라면"),
                    Price.from(1700),
                    Quantity.from(1),
                    PromotionName.from("MD추천상품")
            );

            Products products = new Products(List.of(product1, product2, product3, product4));

            List<Product> productsOnPromotion = products.findProductsOnPromotion();

            assertThat(productsOnPromotion)
                    .hasSize(3)
                    .containsExactlyInAnyOrder(product1, product2, product4)
                    .doesNotContain(product3);
        }
    }
}
