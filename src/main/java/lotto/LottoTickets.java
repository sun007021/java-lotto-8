package lotto;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class LottoTickets {
    private final List<Lotto> lottos;

    public LottoTickets(List<Lotto> lottos) {
        this.lottos = new ArrayList<>(lottos);
    }

    public int getCount() {
        return lottos.size();
    }

    public List<List<LottoNumber>> getAllLottoNumbers() {
        return lottos.stream()
                .map(Lotto::getNumbers)
                .toList();
    }

    public LottoResult checkResults(WinningNumbers winningNumbers) {
        Map<Rank, Integer> rankCounts = new EnumMap<>(Rank.class);

        for (Lotto lotto : lottos) {
            countWinningRank(winningNumbers, lotto, rankCounts);
        }

        return new LottoResult(rankCounts);
    }

    private void countWinningRank(WinningNumbers winningNumbers, Lotto lotto, Map<Rank, Integer> rankCounts) {
        Rank rank = winningNumbers.checkRank(lotto);
        if (rank == Rank.NONE) {
            return;
        }
        rankCounts.merge(rank, 1, Integer::sum);
    }
}
