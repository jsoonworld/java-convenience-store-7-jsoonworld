package store.util;

import static store.exception.ErrorMessage.*;

import store.domain.Promotion;
import store.domain.vo.PromotionName;
import store.domain.vo.RequiredPurchaseQuantity;
import store.domain.vo.FreeQuantity;
import store.domain.vo.PromotionPeriod;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

public class PromotionCsvParser {
    private static final int HEADER_LINE_COUNT = 1;
    private static final int FIELD_COUNT = 5;
    private static final int NAME_INDEX = 0;
    private static final int BUY_INDEX = 1;
    private static final int GET_INDEX = 2;
    private static final int START_DATE_INDEX = 3;
    private static final int END_DATE_INDEX = 4;

    public List<Promotion> parsePromotions(List<String> lines) {
        return lines.stream()
                .skip(HEADER_LINE_COUNT)
                .map(this::parsePromotion)
                .collect(Collectors.toList());
    }

    private Promotion parsePromotion(String line) {
        String[] fields = line.split(",");

        if (fields.length != FIELD_COUNT) {
            throw new IllegalArgumentException(INVALID_PROMOTION_FORMAT.getMessage());
        }

        PromotionName name = parseName(fields[NAME_INDEX]);
        RequiredPurchaseQuantity buy = parseBuyQuantity(fields[BUY_INDEX]);
        FreeQuantity get = parseGetQuantity(fields[GET_INDEX]);
        PromotionPeriod period = parsePeriod(fields[START_DATE_INDEX], fields[END_DATE_INDEX]);

        return Promotion.of(name, buy, get, period);
    }

    private PromotionName parseName(String field) {
        return PromotionName.from(field.trim());
    }

    private RequiredPurchaseQuantity parseBuyQuantity(String field) {
        try {
            return RequiredPurchaseQuantity.from(Integer.parseInt(field.trim()));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(INVALID_INTEGER_FORMAT.getMessage());
        }
    }

    private FreeQuantity parseGetQuantity(String field) {
        try {
            return FreeQuantity.from(Integer.parseInt(field.trim()));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(INVALID_INTEGER_FORMAT.getMessage());
        }
    }

    private PromotionPeriod parsePeriod(String startDateStr, String endDateStr) {
        try {
            LocalDate startDate = LocalDate.parse(startDateStr.trim());
            LocalDate endDate = LocalDate.parse(endDateStr.trim());
            return PromotionPeriod.of(startDate, endDate);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(INVALID_DATE_FORMAT.getMessage());
        }
    }

}
