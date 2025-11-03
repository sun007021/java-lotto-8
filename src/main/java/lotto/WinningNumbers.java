package lotto;

import java.util.List;
import lotto.error.LottoErrorMessage;

public class WinningNumbers {
    private final Lotto winningLotto;
    private final LottoNumber bonusNumber;

    public WinningNumbers(List<LottoNumber> numbers, LottoNumber bonusNumber) {
        this.winningLotto = new Lotto(numbers);
        validateBonusNotDuplicateWithWinningNumbers(bonusNumber);
        this.bonusNumber = bonusNumber;
    }

    private void validateBonusNotDuplicateWithWinningNumbers(LottoNumber bonusNumber) {
        if (winningLotto.contains(bonusNumber)) {
            throw new IllegalArgumentException(LottoErrorMessage.bonusDuplicatesWinning());
        }
    }

    public List<LottoNumber> getWinningNumbers() {
        return winningLotto.getNumbers();
    }

    public LottoNumber getBonusNumber() {
        return bonusNumber;
    }
}