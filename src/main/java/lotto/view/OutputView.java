package lotto.view;

import java.util.List;
import lotto.LottoNumber;
import lotto.LottoResult;
import lotto.LottoTickets;
import lotto.Rank;

public class OutputView {
    private static final String MESSAGE_PURCHASE_COUNT = "%d개를 구매했습니다.";
    private static final String MESSAGE_STATISTICS_HEADER = "당첨 통계";
    private static final String MESSAGE_SEPARATOR = "---";
    private static final String MESSAGE_RANK_FORMAT = "%d개 일치 (%s원) - %d개";
    private static final String MESSAGE_RANK_WITH_BONUS_FORMAT = "%d개 일치, 보너스 볼 일치 (%s원) - %d개";

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

    public static void printResults(LottoResult result) {
        System.out.println();
        System.out.println(MESSAGE_STATISTICS_HEADER);
        System.out.println(MESSAGE_SEPARATOR);
        printRankStatistics(result);
    }

    private static void printRankStatistics(LottoResult result) {
        Rank[] ranks = {Rank.FIFTH, Rank.FOURTH, Rank.THIRD, Rank.SECOND, Rank.FIRST};

        for (Rank rank : ranks) {
            printRankLine(rank, result.getCountByRank(rank));
        }
    }

    private static void printRankLine(Rank rank, int count) {
        String prize = formatPrize(rank.getPrize());

        if (rank.isRequiresBonus()) {
            System.out.println(String.format(
                    MESSAGE_RANK_WITH_BONUS_FORMAT,
                    rank.getMatchCount(), prize, count
            ));
            return;
        }

        System.out.println(String.format(
                MESSAGE_RANK_FORMAT,
                rank.getMatchCount(), prize, count
        ));
    }

    private static String formatPrize(int prize) {
        return String.format("%,d", prize);
    }
}
