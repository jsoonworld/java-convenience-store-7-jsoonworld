package store.domain;

import store.domain.vo.ProductName;
import store.domain.vo.Price;
import store.domain.vo.PromotionName;
import store.domain.vo.Quantity;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

public class Product {
    private final ProductName productName;
    private final Price price;
    private final Quantity quantity;
    private final PromotionName promotionName;

    private Product(ProductName productName, Price price, Quantity quantity, PromotionName promotionName) {
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.promotionName = promotionName;
    }

    public static Product of(ProductName productName, Price price, Quantity quantity, PromotionName promotionName) {
        return new Product(productName, price, quantity, promotionName);
    }

    public String getName() {
        return productName.getName();
    }

    public int getPriceValue() {
        return price.getValue();
    }

    public int getQuantityValue() {
        return quantity.getAmount();
    }

    public boolean hasNoStock() {
        return quantity.isOutOfStock();
    }

    public boolean isPromotional() {
        return !promotionName.isNullPromotion();
    }

    public String getPromotionName() {
        return promotionName.value();
    }

    public Optional<Promotion> getActivePromotion(Promotions promotions, LocalDate date) {
        if (isPromotional()) {
            return findActivePromotion(promotions, date);
        }
        return Optional.empty();
    }

    public Product decreaseQuantity(int amount) {
        Quantity updatedQuantity = quantity.decrease(amount);
        return new Product(productName, price, updatedQuantity, promotionName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product product)) {
            return false;
        }
        return productName.equals(product.productName) &&
                price.equals(product.price) &&
                quantity.equals(product.quantity) &&
                promotionName.equals(product.promotionName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productName, price, quantity, promotionName);
    }

    private Optional<Promotion> findActivePromotion(Promotions promotions, LocalDate date) {
        return promotions.findActivePromotion(promotionName.value(), date);
    }
}
