package store.util;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import store.dto.PurchaseProductRequest;

import java.util.List;

class PurchaseProductParserRequestTest {

    @Nested
    @DisplayName("성공 케이스")
    class SuccessCases {

        @Test
        @DisplayName("유효한 입력값을 DTO 리스트로 변환한다")
        void shouldParseValidInputToDtoList() {
            String input = "[콜라-2],[사이다-3],[물-5]";

            List<PurchaseProductRequest> result = PurchaseProductRequestParser.parse(input);

            assertThat(result).hasSize(3);
            assertThat(result.get(0).getProductName()).isEqualTo("콜라");
            assertThat(result.get(0).getQuantity()).isEqualTo(2);
            assertThat(result.get(1).getProductName()).isEqualTo("사이다");
            assertThat(result.get(1).getQuantity()).isEqualTo(3);
            assertThat(result.get(2).getProductName()).isEqualTo("물");
            assertThat(result.get(2).getQuantity()).isEqualTo(5);
        }
    }
}
