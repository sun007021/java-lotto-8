package lotto;

import lotto.view.InputView;
import lotto.view.OutputView;

public class LottoController {

    public void run() {
        LottoGame game = RetryHandler.retryOnException(this::purchaseLotto);
        OutputView.printPurchasedLottos(game.getTickets());
    }

    private LottoGame purchaseLotto() {
        int amount = readPurchaseAmount();
        return new LottoGame(amount);
    }

    private int readPurchaseAmount() {
        String input = InputView.readPurchaseAmount();
        return Parser.parsePurchaseAmount(input);
    }
}
