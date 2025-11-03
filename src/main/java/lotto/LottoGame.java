package lotto;

import camp.nextstep.edu.missionutils.Randoms;
import java.util.List;
import java.util.stream.Stream;
import lotto.error.LottoGameErrorMessage;

public class LottoGame {
    private static final int LOTTO_PRICE = 1000;

    private final LottoTickets tickets;

    public LottoGame(int purchaseAmount) {
        validatePurchaseAmount(purchaseAmount);
        this.tickets = purchaseLottos(purchaseAmount);
    }

    private void validatePurchaseAmount(int amount) {
        if (amount % LOTTO_PRICE != 0) {
            throw new IllegalArgumentException(LottoGameErrorMessage.invalidPurchaseAmountUnit(LOTTO_PRICE));
        }
    }

    private LottoTickets purchaseLottos(int purchaseAmount) {
        int lottoCount = purchaseAmount / LOTTO_PRICE;
        List<Lotto> lottos = Stream.generate(this::generateLotto)
                .limit(lottoCount)
                .toList();
        return new LottoTickets(lottos);
    }

    private Lotto generateLotto() {
        List<Integer> numbers = Randoms.pickUniqueNumbersInRange(1, 45, 6);
        return new Lotto(numbers);
    }

    public LottoTickets getTickets() {
        return tickets;
    }
}
