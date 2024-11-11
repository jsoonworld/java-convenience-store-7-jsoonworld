package store.domain.vo.promotion;

import static store.exception.ErrorMessage.*;

import java.time.LocalDate;

public class PromotionPeriod {
    private final LocalDate startDate;
    private final LocalDate endDate;

    private PromotionPeriod(LocalDate startDate, LocalDate endDate) {
        validate(startDate, endDate);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static PromotionPeriod of(LocalDate startDate, LocalDate endDate) {
        return new PromotionPeriod(startDate, endDate);
    }

    public boolean isWithinPeriod(LocalDate dateTime) {
        return !dateTime.isBefore(startDate) && !dateTime.isAfter(endDate);
    }

    private void validate(LocalDate startDate, LocalDate endDate) {
        validateStartDateNotNull(startDate);
        validateEndDateNotNull(endDate);
        validateStartDateBeforeOrEqualEndDate(startDate, endDate);
    }

    private void validateStartDateNotNull(LocalDate startDate) {
        if (startDate == null) {
            throw new IllegalArgumentException(NULL_START_DATE.getMessage());
        }
    }

    private void validateEndDateNotNull(LocalDate endDate) {
        if (endDate == null) {
            throw new IllegalArgumentException(NULL_END_DATE.getMessage());
        }
    }

    private void validateStartDateBeforeOrEqualEndDate(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException(INVALID_PROMOTION_PERIOD.getMessage());
        }
    }
}
