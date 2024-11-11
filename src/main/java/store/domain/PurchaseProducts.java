package store.domain;

import store.dto.request.purchase.PurchaseProductRequest;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PurchaseProducts {
    private final List<PurchaseProduct> products;

    private PurchaseProducts(List<PurchaseProduct> products) {
        this.products = products;
    }

    public static PurchaseProducts fromRequests(List<PurchaseProductRequest> purchaseProductRequests) {
        List<PurchaseProduct> products = purchaseProductRequests.stream()
                .map(PurchaseProductRequest::toPurchaseProduct)
                .collect(Collectors.toList());
        return new PurchaseProducts(products);
    }

    public List<PurchaseProduct> getProducts() {
        return Collections.unmodifiableList(products);
    }
}
