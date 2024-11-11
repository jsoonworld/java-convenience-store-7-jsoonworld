package store.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class Promotions {
    private final List<Promotion> promotions;

    public Promotions(List<Promotion> promotions) {
        this.promotions = promotions;
    }

    public Optional<Promotion> findActivePromotion(String name, LocalDate date) {
        return promotions.stream()
                .filter(promo -> promo.getName().equals(name) && promo.isActiveOn(date))
                .findFirst();
    }
}
