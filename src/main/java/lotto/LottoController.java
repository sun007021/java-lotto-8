package lotto;

import lotto.view.InputView;

public class LottoController {

    public void run() {
        LottoGame game = RetryHandler.retryOnException(this::purchaseLotto);
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
