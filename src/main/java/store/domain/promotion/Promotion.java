package store.domain.promotion;

import store.domain.vo.PromotionName;
import store.domain.vo.promotion.RequiredPurchaseQuantity;
import store.domain.vo.promotion.FreeQuantity;
import store.domain.vo.promotion.PromotionPeriod;

import java.time.LocalDate;
import java.util.Objects;

public class Promotion {
    private final PromotionName name;
    private final RequiredPurchaseQuantity buyQuantity;
    private final FreeQuantity freeQuantity;
    private final PromotionPeriod period;

    private Promotion(PromotionName name, RequiredPurchaseQuantity buyQuantity, FreeQuantity freeQuantity, PromotionPeriod period) {
        this.name = name;
        this.buyQuantity = buyQuantity;
        this.freeQuantity = freeQuantity;
        this.period = period;
    }

    public static Promotion of(PromotionName name, RequiredPurchaseQuantity buy, FreeQuantity get, PromotionPeriod period) {
        return new Promotion(name, buy, get, period);
    }

    public boolean isActiveOn(LocalDate date) {
        return period.isWithinPeriod(date);
    }

    public String getName() {
        return name.value();
    }

    public int getBuyQuantity() {
        return buyQuantity.getValue();
    }

    public int getFreeQuantity() {
        return freeQuantity.value();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Promotion)) {
            return false;
        }
        Promotion promotion = (Promotion) o;
        return buyQuantity.equals(promotion.buyQuantity) &&
                freeQuantity.equals(promotion.freeQuantity) &&
                period.equals(promotion.period) &&
                name.equals(promotion.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, buyQuantity, freeQuantity, period);
    }
}
