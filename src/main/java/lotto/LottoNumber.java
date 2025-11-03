package lotto;

import java.util.Objects;
import lotto.error.LottoErrorMessage;

public class LottoNumber {
    private static final int MIN_RANGE = 1;
    private static final int MAX_RANGE = 45;

    private final int value;

    public LottoNumber(int value) {
        validateRange(value);
        this.value = value;
    }

    private void validateRange(int value) {
        if (value < MIN_RANGE || value > MAX_RANGE) {
            throw new IllegalArgumentException(LottoErrorMessage.invalidNumberRange(MIN_RANGE, MAX_RANGE));
        }
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LottoNumber that = (LottoNumber) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
