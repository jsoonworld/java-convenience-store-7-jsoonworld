package store.exception;

public enum ErrorMessage {
    FILE_NOT_FOUND("[ERROR] 해당 파일을 찾을 수 없습니다."),
    FILE_READ_ERROR("[ERROR] 파일을 읽을 수 없습니다."),

    EMPTY_PRODUCT_NAME("[ERROR] 상품 이름은 비어 있을 수 없습니다. 유효한 이름을 입력해 주세요."),
    NULL_PRODUCT_NAME("[ERROR] 상품 이름은 null일 수 없습니다."),
    NULL_PRICE("[ERROR] 가격은 null일 수 없습니다."),
    NEGATIVE_PRICE("[ERROR] 가격은 0 이상이어야 합니다. 음수는 허용되지 않습니다."),
    NULL_QUANTITY("[ERROR] 수량은 null일 수 없습니다."),
    NEGATIVE_QUANTITY("[ERROR] 수량은 0 이상이어야 합니다. 음수는 허용되지 않습니다."),
    INVALID_PROMOTION_NAME("[ERROR] 프로모션 이름은 null일 수 없습니다."),

    FILE_LOAD_FAILURE("[ERROR] 파일 데이터를 로드하는 데 실패했습니다."),

    NEGATIVE_FREE_QUANTITY("[ERROR] 무료 제공 수량은 음수일 수 없습니다."),
    INVALID_PROMOTION_PERIOD("[ERROR] 프로모션 시작 날짜는 종료 날짜 이후일 수 없습니다."),
    NEGATIVE_PROMOTION_QUANTITY("[ERROR] 프로모션 수량은 음수일 수 없습니다."),

    NULL_START_DATE("[ERROR] 프로모션 시작 날짜는 null일 수 없습니다."),
    NULL_END_DATE("[ERROR] 프로모션 종료 날짜는 null일 수 없습니다."),

    INPUT_CANNOT_BE_NULL("[ERROR] 입력은 null일 수 없습니다."),
    INPUT_CANNOT_BE_EMPTY("[ERROR] 입력은 비어 있을 수 없습니다."),
    INVALID_FORMAT("[ERROR] 입력 형식이 올바르지 않습니다."),
    INVALID_QUANTITY_FORMAT("[ERROR] 수량은 숫자여야 합니다."),

    PURCHASE_INPUT_CANNOT_BE_NULL("[ERROR] 구매 항목 입력값은 null일 수 없습니다."),
    PURCHASE_INPUT_CANNOT_BE_EMPTY("[ERROR] 구매 항목 입력값은 빈 값일 수 없습니다."),

    ITEM_MUST_BE_IN_BRACKETS("[ERROR] 각 상품은 대괄호([])로 묶여야 합니다."),
    ITEM_MUST_CONTAIN_NAME_AND_QUANTITY("[ERROR] 상품명과 수량은 하이픈(-)으로 구분되어야 합니다."),
    PRODUCT_NAME_CANNOT_BE_EMPTY("[ERROR] 상품명은 비어 있을 수 없습니다."),
    QUANTITY_CANNOT_BE_EMPTY("[ERROR] 수량은 비어 있을 수 없습니다."),
    QUANTITY_MUST_BE_NUMERIC("[ERROR] 수량은 숫자여야 합니다."),
    QUANTITY_MUST_BE_POSITIVE("[ERROR] 수량은 1 이상이어야 합니다."),
    INPUT_CANNOT_START_OR_END_WITH_COMMA("[ERROR] 입력은 쉼표로 시작하거나 끝날 수 없습니다."),
    EMPTY_ITEM_INCLUDED("[ERROR] 빈 항목이 포함되어 있습니다. 유효한 상품 정보를 입력해주세요."),
    PRODUCT_NAME_CANNOT_BE_NULL("[ERROR] 상품명은 null일 수 없습니다."),
    PRODUCT_NAME_CANNOT_BE_BLANK("[ERROR] 상품명은 비어있을 수 없습니다."),

    INVALID_PROMOTION_FORMAT("[ERROR] 잘못된 프로모션 형식입니다: "),
    INVALID_INTEGER_FORMAT("[ERROR] 숫자 형식이 올바르지 않습니다: "),
    INVALID_DATE_FORMAT("[ERROR] 날짜 형식이 올바르지 않습니다: "),

    PRODUCT_DATA_INITIALIZATION_ERROR("[ERROR] 상품 데이터를 초기화하는 중 오류가 발생했습니다: "),
    PROMOTION_DATA_INITIALIZATION_ERROR("[ERROR] 프로모션 데이터를 초기화하는 중 오류가 발생했습니다."),

    ;

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
