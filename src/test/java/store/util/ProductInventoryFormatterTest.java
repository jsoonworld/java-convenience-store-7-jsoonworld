package store.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.Product;
import store.domain.vo.Price;
import store.domain.vo.ProductName;
import store.domain.vo.PromotionName;
import store.domain.vo.Quantity;

import java.util.List;

@DisplayName("ProductInventoryFormatter 클래스 테스트")
class ProductInventoryFormatterTest {

    @Test
    @DisplayName("제품 목록을 포맷하여 올바른 형식의 문자열 목록을 반환한다")
    void formatProductList() {
        Product product1 = Product.of(
                ProductName.from("콜라"),
                Price.from(1000),
                Quantity.from(10),
                PromotionName.from("탄산2+1")
        );

        Product product2 = Product.of(
                ProductName.from("사이다"),
                Price.from(800),
                Quantity.from(0),
                PromotionName.from("null")
        );

        List<Product> products = List.of(product1, product2);
        List<String> formattedProducts = ProductInventoryFormatter.format(products);

        assertThat(formattedProducts).hasSize(2);
        assertThat(formattedProducts.get(0)).isEqualTo("- 콜라 1,000원 10개 탄산2+1");
        assertThat(formattedProducts.get(1)).isEqualTo("- 사이다 800원 재고 없음");
    }

    @Test
    @DisplayName("프로모션이 없는 제품의 경우 빈 프로모션 정보를 표시하지 않는다")
    void formatProductWithoutPromotion() {
        Product product = Product.of(
                ProductName.from("사이다"),
                Price.from(800),
                Quantity.from(5),
                PromotionName.from("null")
        );

        List<String> formattedProducts = ProductInventoryFormatter.format(List.of(product));

        assertThat(formattedProducts).hasSize(1);
        assertThat(formattedProducts.get(0)).isEqualTo("- 사이다 800원 5개");
    }

    @Test
    @DisplayName("재고가 없는 경우 '재고 없음' 메시지를 표시한다")
    void formatOutOfStockProduct() {
        Product product = Product.of(
                ProductName.from("사이다"),
                Price.from(800),
                Quantity.from(0),
                PromotionName.from("null")
        );

        List<String> formattedProducts = ProductInventoryFormatter.format(List.of(product));

        assertThat(formattedProducts).hasSize(1);
        assertThat(formattedProducts.get(0)).isEqualTo("- 사이다 800원 재고 없음");
    }

    @Test
    @DisplayName("가격이 올바르게 포맷되어 표시된다")
    void formatProductWithFormattedPrice() {
        Product product = Product.of(
                ProductName.from("콜라"),
                Price.from(1000),
                Quantity.from(1),
                PromotionName.from("탄산2+1")
        );

        List<String> formattedProducts = ProductInventoryFormatter.format(List.of(product));

        assertThat(formattedProducts).hasSize(1);
        assertThat(formattedProducts.get(0)).isEqualTo("- 콜라 1,000원 1개 탄산2+1");
    }
}
