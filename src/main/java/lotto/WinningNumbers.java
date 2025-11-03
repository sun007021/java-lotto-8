package lotto;

import java.util.List;
import lotto.error.LottoErrorMessage;

public class WinningNumbers {
    private final Lotto winningLotto;
    private final LottoNumber bonusNumber;

    public WinningNumbers(Lotto winningLotto, LottoNumber bonusNumber) {
        validateBonusNotDuplicateWithWinningNumbers(winningLotto, bonusNumber);
        this.winningLotto = winningLotto;
        this.bonusNumber = bonusNumber;
    }

    private void validateBonusNotDuplicateWithWinningNumbers(Lotto winningLotto, LottoNumber bonusNumber) {
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