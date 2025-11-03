package lotto;

import lotto.view.InputView;
import lotto.view.OutputView;

public class LottoController {

    public void run() {
        LottoGame game = RetryHandler.retryOnException(this::purchaseLotto);
        OutputView.printPurchasedLottos(game.getTickets());

        WinningNumbers winningNumbers = createWinningNumbers();
    }

    private LottoGame purchaseLotto() {
        int amount = readPurchaseAmount();
        return new LottoGame(amount);
    }

    private int readPurchaseAmount() {
        String input = InputView.readPurchaseAmount();
        return Parser.parsePurchaseAmount(input);
    }

    private WinningNumbers createWinningNumbers() {
        Lotto winningLotto = RetryHandler.retryOnException(this::readWinningLotto);
        return RetryHandler.retryOnException(() -> createWinningNumbersWithBonus(winningLotto));
    }

    private WinningNumbers createWinningNumbersWithBonus(Lotto winningLotto) {
        LottoNumber bonusNumber = RetryHandler.retryOnException(this::readBonusNumber);
        return new WinningNumbers(winningLotto, bonusNumber);
    }

    private Lotto readWinningLotto() {
        String input = InputView.readWinningNumbers();
        return new Lotto(Parser.parseWinningNumbers(input));
    }

    private LottoNumber readBonusNumber() {
        String input = InputView.readBonusNumber();
        return Parser.parseBonusNumber(input);
    }
}
