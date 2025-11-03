package lotto;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import lotto.error.LottoErrorMessage;

public class Lotto {
    private static final int LOTTO_NUMBER_COUNT = 6;
    private static final int LOTTO_NUMBER_MIN_RANGE = 1;
    private static final int LOTTO_NUMBER_MAX_RANGE = 45;

    private final List<Integer> numbers;

    public Lotto(List<Integer> numbers) {
        validate(numbers);
        this.numbers = numbers.stream()
                .sorted()
                .toList();
    }

    private void validate(List<Integer> numbers) {
        validateNumberCount(numbers);
        validateNumberRange(numbers);
        validateDuplicateNumber(numbers);
    }

    private void validateNumberCount(List<Integer> numbers) {
        if (numbers.size() != LOTTO_NUMBER_COUNT) {
            throw new IllegalArgumentException(LottoErrorMessage.invalidNumberCount(LOTTO_NUMBER_COUNT));
        }
    }

    private void validateNumberRange(List<Integer> numbers) {
        for (Integer number : numbers) {
            if (number < LOTTO_NUMBER_MIN_RANGE || number > LOTTO_NUMBER_MAX_RANGE) {
                throw new IllegalArgumentException(LottoErrorMessage.invalidNumberRange(LOTTO_NUMBER_MIN_RANGE,
                        LOTTO_NUMBER_MAX_RANGE));
            }
        }
    }

    private void validateDuplicateNumber(List<Integer> numbers) {
        if (new HashSet<>(numbers).size() != numbers.size()) {
            throw new IllegalArgumentException(LottoErrorMessage.duplicateNumbers());
        }
    }

    public List<Integer> getNumbers() {
        return List.copyOf(numbers);
    }
}
