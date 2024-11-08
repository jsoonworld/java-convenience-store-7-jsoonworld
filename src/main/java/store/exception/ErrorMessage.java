package store.exception;

public enum ErrorMessage {
    FILE_NOT_FOUND("[ERROR] 해당 파일을 찾을 수 없습니다."),
    FILE_READ_ERROR("[ERROR] 파일을 읽을 수 없습니다."),

    EMPTY_PRODUCT_NAME("상품 이름은 비어 있을 수 없습니다. 유효한 이름을 입력해 주세요."),
    NULL_PRODUCT_NAME("상품 이름은 null일 수 없습니다."),
    NULL_PRICE("가격은 null일 수 없습니다."),
    NEGATIVE_PRICE("가격은 0 이상이어야 합니다. 음수는 허용되지 않습니다."),
    NULL_QUANTITY("수량은 null일 수 없습니다."),
    NEGATIVE_QUANTITY("수량은 0 이상이어야 합니다. 음수는 허용되지 않습니다."),
    INVALID_PROMOTION_NAME("프로모션 이름은 null일 수 없습니다."),

    FILE_LOAD_FAILURE("파일 데이터를 로드하는 데 실패했습니다."),

    NEGATIVE_FREE_QUANTITY("무료 제공 수량은 음수일 수 없습니다."),
    INVALID_PROMOTION_PERIOD("프로모션 시작 날짜는 종료 날짜 이후일 수 없습니다."),
    NEGATIVE_PROMOTION_QUANTITY("프로모션 수량은 음수일 수 없습니다."),

    NULL_START_DATE("프로모션 시작 날짜는 null일 수 없습니다."),
    NULL_END_DATE("프로모션 종료 날짜는 null일 수 없습니다.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
