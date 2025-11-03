package lotto.error;

public enum LottoErrorMessage {
    INVALID_NUMBER_COUNT("로또 번호는 %d개여야 합니다."),
    INVALID_NUMBER_RANGE("로또 번호는 %d부터 %d 사이의 숫자여야 합니다."),
    DUPLICATE_NUMBER("로또 번호에는 중복이 없어야 합니다."),
    BONUS_DUPLICATES_WINNING("보너스 번호는 당첨 번호와 중복될 수 없습니다.");

    private final String PREFIX = "[ERROR] ";
    private final String template;

    LottoErrorMessage(String template) {
        this.template = template;
    }

    private String format(Object... args) {
        return PREFIX + String.format(template, args);
    }

    public static String invalidNumberCount(int count) {
        return INVALID_NUMBER_COUNT.format(count);
    }

    public static String invalidNumberRange(int min, int max) {
        return INVALID_NUMBER_RANGE.format(min, max);
    }

    public static String duplicateNumbers() {
        return DUPLICATE_NUMBER.format();
    }

    public static String bonusDuplicatesWinning() {
        return BONUS_DUPLICATES_WINNING.format();
    }
}
