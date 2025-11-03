package lotto;

import java.util.function.Supplier;
import lotto.error.ErrorHandler;
import lotto.view.InputView;

public class LottoController {

    public void run() {
        LottoGame game = retryOnException(this::purchaseLotto);
    }

    private LottoGame purchaseLotto() {
        int amount = readPurchaseAmount();
        return new LottoGame(amount);
    }

    private int readPurchaseAmount() {
        String input = InputView.readPurchaseAmount();
        return Parser.parsePurchaseAmount(input);
    }

    private <T> T retryOnException(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                ErrorHandler.handle(e);
            }
        }
    }
}
