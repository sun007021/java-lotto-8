package lotto;

import lotto.error.InputErrorMessage;

public class Parser {

    public static int parsePurchaseAmount(String input) {
        String trimmed = input.trim();
        String numberOnly = removeUnit(trimmed);
        validateNotEmpty(numberOnly);
        return parseInteger(numberOnly);
    }

    private static String removeUnit(String input) { // 입력값에 금액 단위 제거
        return input.replaceAll("[^0-9]", "");
    }

    private static void validateNotEmpty(String input) {
        if (input.isEmpty()) {
            throw new IllegalArgumentException(InputErrorMessage.invalidNumberFormat());
        }
    }

    private static int parseInteger(String input) {
        try {
            long value = Long.parseLong(input);
            if (value > Integer.MAX_VALUE || value < Integer.MIN_VALUE) {
                throw new IllegalArgumentException(InputErrorMessage.numberOverflow());
            }
            return (int) value;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(InputErrorMessage.invalidNumberFormat());
        }
    }

}
