package store.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import store.domain.Product;

import java.util.List;

@DisplayName("ProductCsvParser 클래스 테스트")
class ProductCsvParserTest {

    private final ProductCsvParser parser = new ProductCsvParser();

    @Nested
    @DisplayName("성공 케이스")
    class SuccessCases {

        @Test
        @DisplayName("유효한 CSV 데이터를 파싱하여 Product 객체 목록을 반환한다")
        void parseProductsSuccessfully() {
            List<String> lines = List.of(
                    "name,price,quantity,promotion",
                    "콜라,1000,10,탄산2+1",
                    "사이다,1000,8,null"
            );

            List<Product> products = parser.parseProducts(lines);

            assertThat(products).hasSize(2);
            assertThat(products.get(0)).satisfies(product -> {
                assertThat(product.getName()).isEqualTo("콜라");
                assertThat(product.getPriceValue()).isEqualTo(1000);
                assertThat(product.getQuantityValue()).isEqualTo(10);
                assertThat(product.getPromotionName()).isEqualTo("탄산2+1");
            });

            assertThat(products.get(1)).satisfies(product -> {
                assertThat(product.getName()).isEqualTo("사이다");
                assertThat(product.getPriceValue()).isEqualTo(1000);
                assertThat(product.getQuantityValue()).isEqualTo(8);
                assertThat(product.isPromotional()).isFalse();
            });
        }

        @Test
        @DisplayName("프로모션 필드가 'null'일 경우 빈 프로모션으로 설정된다")
        void parseProductWithNullPromotion() {
            List<String> lines = List.of(
                    "name,price,quantity,promotion",
                    "물,500,10,null"
            );

            List<Product> products = parser.parseProducts(lines);

            assertThat(products).hasSize(1);
            assertThat(products.get(0)).satisfies(product -> {
                assertThat(product.getName()).isEqualTo("물");
                assertThat(product.getPriceValue()).isEqualTo(500);
                assertThat(product.getQuantityValue()).isEqualTo(10);
                assertThat(product.isPromotional()).isFalse();
            });
        }

        @Test
        @DisplayName("프로모션 필드가 누락된 경우 빈 프로모션으로 설정된다")
        void parseProductWithoutPromotionField() {
            List<String> lines = List.of(
                    "name,price,quantity",
                    "물,500,10"
            );

            List<Product> products = parser.parseProducts(lines);

            assertThat(products).hasSize(1);
            assertThat(products.get(0)).satisfies(product -> {
                assertThat(product.getName()).isEqualTo("물");
                assertThat(product.getPriceValue()).isEqualTo(500);
                assertThat(product.getQuantityValue()).isEqualTo(10);
                assertThat(product.isPromotional()).isFalse();
            });
        }
    }

    @Nested
    @DisplayName("실패 케이스")
    class FailureCases {

        @Test
        @DisplayName("잘못된 형식의 가격 데이터로 파싱 시 예외 발생")
        void throwExceptionWhenInvalidPriceFormat() {
            List<String> lines = List.of(
                    "name,price,quantity,promotion",
                    "콜라,invalid,10,탄산2+1"
            );

            assertThatThrownBy(() -> parser.parseProducts(lines))
                    .isInstanceOf(NumberFormatException.class);
        }

        @Test
        @DisplayName("잘못된 형식의 수량 데이터로 파싱 시 예외 발생")
        void throwExceptionWhenInvalidQuantityFormat() {
            List<String> lines = List.of(
                    "name,price,quantity,promotion",
                    "콜라,1000,invalid,탄산2+1"
            );

            assertThatThrownBy(() -> parser.parseProducts(lines))
                    .isInstanceOf(NumberFormatException.class);
        }

        @Test
        @DisplayName("필수 필드가 누락된 경우 예외 발생")
        void throwExceptionWhenRequiredFieldIsMissing() {
            List<String> lines = List.of(
                    "name,price,quantity",
                    "콜라,1000" // quantity 필드가 누락된 경우
            );

            assertThatThrownBy(() -> parser.parseProducts(lines))
                    .isInstanceOf(IndexOutOfBoundsException.class);
        }
    }
}
