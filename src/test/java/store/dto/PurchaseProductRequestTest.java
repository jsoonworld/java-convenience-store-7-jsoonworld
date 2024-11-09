package store.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.PurchaseProduct;
import store.domain.vo.PurchaseProductName;
import store.domain.vo.PurchaseQuantity;

class PurchaseProductRequestTest {

    @Test
    @DisplayName("유효한 productName과 quantity로 PurchaseProductRequest 객체를 생성하고, PurchaseProduct로 변환한다.")
    void createAndConvertToPurchaseProduct() {
        // given
        String productName = "콜라";
        int quantity = 3;

        // when
        PurchaseProductRequest request = PurchaseProductRequest.of(productName, quantity);
        PurchaseProduct purchaseProduct = request.toPurchaseProduct();

        // then
        assertThat(purchaseProduct.getPurchasedProductName()).isEqualTo(PurchaseProductName.of(productName).getName());
        assertThat(purchaseProduct.getPurchasedQuantityValue()).isEqualTo(PurchaseQuantity.of(quantity).getAmount());
    }
}
