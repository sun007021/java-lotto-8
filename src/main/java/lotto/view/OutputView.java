package lotto.view;

import java.util.List;
import lotto.LottoNumber;
import lotto.LottoTickets;

public class OutputView {
    private static final String MESSAGE_PURCHASE_COUNT = "%d개를 구매했습니다.";

    public static void printPurchasedLottos(LottoTickets tickets) {
        printPurchaseCount(tickets.getCount());
        printLottoNumbers(tickets.getAllLottoNumbers());
    }

    private static void printPurchaseCount(int count) {
        System.out.println(String.format(MESSAGE_PURCHASE_COUNT, count));
    }

    private static void printLottoNumbers(List<List<LottoNumber>> lottoNumbersList) {
        lottoNumbersList.forEach(System.out::println);
    }
}
