package store.exception;

public enum ErrorMessage {

    // 파일 관련 에러 메시지
    FILE_LOAD_FAILURE("[ERROR] 파일 데이터를 로드하는 데 실패했습니다."),
    FILE_NOT_FOUND("[ERROR] 해당 파일을 찾을 수 없습니다."),
    FILE_READ_ERROR("[ERROR] 파일을 읽을 수 없습니다."),
    FILE_SAVE_ERROR("[ERROR] 파일 저장 중 오류가 발생했습니다."),
    PRODUCT_FILE_PATH_LOAD_ERROR("[ERROR] 상품 파일 경로를 불러오는 중 오류가 발생했습니다."),
    PROMOTION_FILE_PATH_LOAD_ERROR("[ERROR] 프로모션 파일 경로를 불러오는 중 오류가 발생했습니다."),

    // 입력 관련 에러 메시지
    EXCEEDS_STOCK("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요."),
    INPUT_CANNOT_BE_EMPTY("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요."),
    INPUT_CANNOT_BE_NULL("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요."),
    INVALID_FORMAT("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."),
    INVALID_INPUT_MESSAGE("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요."),
    INVALID_QUANTITY_FORMAT("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."),
    PRODUCT_NOT_FOUND("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요."),
    PURCHASE_INPUT_CANNOT_BE_EMPTY("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."),
    PURCHASE_INPUT_CANNOT_BE_NULL("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."),

    // 상품 데이터 관련 에러 메시지
    EMPTY_PRODUCT_NAME("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."),
    INSUFFICIENT_STOCK("[ERROR] 재고가 부족합니다."),
    NEGATIVE_PRICE("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."),
    NEGATIVE_QUANTITY("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."),
    NULL_PRICE("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."),
    NULL_PRODUCT_NAME("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."),
    NULL_QUANTITY("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."),
    PRODUCT_DATA_INITIALIZATION_ERROR("[ERROR] 상품 데이터를 초기화하는 중 오류가 발생했습니다: "),
    PRODUCT_SAVE_FAILED("[ERROR] 상품 저장에 실패 하였습니다."),

    // 프로모션 관련 에러 메시지
    DUPLICATE_PROMOTION("[ERROR] 중복된 프로모션이 존재합니다."),
    INVALID_PROMOTION_FORMAT("[ERROR] 잘못된 프로모션 형식입니다: "),
    INVALID_PROMOTION_NAME("[ERROR] 프로모션 이름은 null일 수 없습니다."),
    INVALID_PROMOTION_PERIOD("[ERROR] 프로모션 시작 날짜는 종료 날짜 이후일 수 없습니다."),
    NEGATIVE_FREE_QUANTITY("[ERROR] 무료 제공 수량은 음수일 수 없습니다."),
    NEGATIVE_PROMOTION_QUANTITY("[ERROR] 프로모션 수량은 음수일 수 없습니다."),
    NULL_END_DATE("[ERROR] 프로모션 종료 날짜는 null일 수 없습니다."),
    NULL_START_DATE("[ERROR] 프로모션 시작 날짜는 null일 수 없습니다."),
    PROMOTION_DATA_INITIALIZATION_ERROR("[ERROR] 프로모션 데이터를 초기화하는 중 오류가 발생했습니다."),

    // 형식 관련 에러 메시지
    INVALID_DATE_FORMAT("[ERROR] 날짜 형식이 올바르지 않습니다: "),
    INVALID_INTEGER_FORMAT("[ERROR] 숫자 형식이 올바르지 않습니다: "),

    // 기타 에러 메시지
    DIVISION_BY_ZERO("[ERROR] 구매 수량이 0으로 나누어졌습니다."),
    INVALID_INDEX("[ERROR] 유효하지 않은 인덱스입니다. 상품 추가 실패."),
    INVALID_INPUT_MESSAGE_GENERAL("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요."),

    // 기타 입력 형식 관련 에러 메시지
    EMPTY_ITEM_INCLUDED("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."),
    INPUT_CANNOT_START_OR_END_WITH_COMMA("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."),
    ITEM_MUST_BE_IN_BRACKETS("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."),
    ITEM_MUST_CONTAIN_NAME_AND_QUANTITY("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."),
    PRODUCT_NAME_CANNOT_BE_BLANK("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."),
    PRODUCT_NAME_CANNOT_BE_EMPTY("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."),
    PRODUCT_NAME_CANNOT_BE_NULL("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."),
    QUANTITY_CANNOT_BE_EMPTY("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."),
    QUANTITY_MUST_BE_NUMERIC("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."),
    QUANTITY_MUST_BE_POSITIVE("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
