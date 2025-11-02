package lotto.error;

public enum LottoGameErrorMessage {
    INVALID_PURCHASE_AMOUNT_UNIT("구입 금액은 %d원 단위여야 합니다.");

    private final String PREFIX = "[ERROR] ";
    private final String template;

    LottoGameErrorMessage(String template) {
        this.template = template;
    }

    private String format(Object... args) {
        return PREFIX + String.format(template, args);
    }

    public static String invalidPurchaseAmountUnit(int unit) {
        return INVALID_PURCHASE_AMOUNT_UNIT.format(unit);
    }
}