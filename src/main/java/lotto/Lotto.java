package lotto;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import lotto.error.LottoErrorMessage;

public class Lotto {
    private static final int LOTTO_NUMBER_COUNT = 6;

    private final List<LottoNumber> numbers;

    public Lotto(List<LottoNumber> numbers) {
        validate(numbers);
        this.numbers = numbers.stream()
                .sorted(Comparator.comparingInt(LottoNumber::getValue))
                .toList();
    }

    private void validate(List<LottoNumber> numbers) {
        validateNumberCount(numbers);
        validateDuplicateNumber(numbers);
    }

    private void validateNumberCount(List<LottoNumber> numbers) {
        if (numbers.size() != LOTTO_NUMBER_COUNT) {
            throw new IllegalArgumentException(LottoErrorMessage.invalidNumberCount(LOTTO_NUMBER_COUNT));
        }
    }

    private void validateDuplicateNumber(List<LottoNumber> numbers) {
        if (new HashSet<>(numbers).size() != numbers.size()) {
            throw new IllegalArgumentException(LottoErrorMessage.duplicateNumbers());
        }
    }

    public List<LottoNumber> getNumbers() {
        return List.copyOf(numbers);
    }
}
