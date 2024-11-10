package store.domain;

import java.util.List;
import java.util.stream.Collectors;

public class Products {
    private final List<Product> products;

    public Products(List<Product> products) {
        this.products = products;
    }

    public List<Product> findProductsOnPromotion() {
        return products.stream()
                .filter(Product::isPromotional)
                .collect(Collectors.toList());
    }
}
