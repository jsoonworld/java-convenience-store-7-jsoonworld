package store.util.formatter;

import store.domain.product.Product;
import store.dto.request.purchase.PurchaseDetailsRequest;

public class PurchaseDetailsFormatter {

    public static String formatPurchaseDetails(Product product, int totalFinalQuantity) {
        PurchaseDetailsRequest request = PurchaseDetailsRequest.of(product, totalFinalQuantity, product.getPriceValue());
        return String.format("%-10s\t%d\t%,d\n", request.getProductName(), request.getQuantity(), request.getTotalProductPrice());
    }
}
