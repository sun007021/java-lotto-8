package lotto.error;

public enum InputErrorMessage {
    INVALID_NUMBER_FORMAT("숫자 형식이 올바르지 않습니다."),
    NUMBER_OVERFLOW("입력 값이 너무 큽니다.");

    private final String PREFIX = "[ERROR] ";
    private final String template;

    InputErrorMessage(String template) {
        this.template = template;
    }

    private String format(Object... args) {
        return PREFIX + String.format(template, args);
    }

    public static String invalidNumberFormat() {
        return INVALID_NUMBER_FORMAT.format();
    }

    public static String numberOverflow() {
        return NUMBER_OVERFLOW.format();
    }
}