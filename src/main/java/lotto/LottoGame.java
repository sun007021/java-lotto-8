package lotto;

public class LottoGame {
    private static final int LOTTO_PRICE = 1000;

    private final LottoTickets tickets;

    public LottoGame(int purchaseAmount) {
        validatePurchaseAmount(purchaseAmount);
        this.tickets = purchaseLottos(purchaseAmount);
    }

    private void validatePurchaseAmount(int amount) {
        if (amount % LOTTO_PRICE != 0) {
            throw new IllegalArgumentException(
                String.format("[ERROR] 구입 금액은 %d원 단위여야 합니다.", LOTTO_PRICE)
            );
        }
    }

    private LottoTickets purchaseLottos(int purchaseAmount) {
        // TODO: 로또 생성 로직 구현 필요
        return new LottoTickets(List.of());
    }

    public LottoTickets getTickets() {
        return tickets;
    }
}
