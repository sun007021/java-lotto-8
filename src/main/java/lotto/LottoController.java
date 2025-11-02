package lotto;

import lotto.view.InputView;

public class LottoController {

    public void run(){
        int purshaseAmount = readPurchaseAmount();
    }

    private int readPurchaseAmount(){
        String purchaseAmount = InputView.readPurchasAmount();
        return Parser.parsePurchaseAmount(purchaseAmount);
    }
}
