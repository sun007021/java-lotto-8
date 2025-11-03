package lotto;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lotto.error.InputErrorMessage;

public class Parser {

    private static final String DELIMITER = ",";

    public static int parsePurchaseAmount(String input) {
        String trimmed = input.trim();
        String numberOnly = removeUnit(trimmed);
        validateNotEmpty(numberOnly);
        return parseInteger(numberOnly);
    }

    public static List<LottoNumber> parseWinningNumbers(String input) {
        String trimmed = input.trim();
        String[] tokens = trimmed.split(DELIMITER);

        return Arrays.stream(tokens)
                .map(String::trim)
                .map(Parser::parsePositiveInt)
                .map(LottoNumber::new)
                .collect(Collectors.toList());
    }

    public static LottoNumber parseBonusNumber(String input) {
        String trimmed = input.trim();
        int number = parsePositiveInt(trimmed);
        return new LottoNumber(number);
    }

    private static String removeUnit(String input) {
        return input.replace("원", "");
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

    private static int parsePositiveInt(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(InputErrorMessage.invalidNumberFormat());
        }
    }

}
