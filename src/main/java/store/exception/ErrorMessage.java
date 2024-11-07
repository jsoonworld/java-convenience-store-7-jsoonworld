package store.exception;

public enum ErrorMessage {
    FILE_NOT_FOUND("[ERROR] 해당 파일을 찾을 수 없습니다."),
    FILE_READ_ERROR("[ERROR] 파일을 읽을 수 없습니다.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
