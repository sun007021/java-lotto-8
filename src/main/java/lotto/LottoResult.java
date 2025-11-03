package lotto;

import java.util.EnumMap;
import java.util.Map;

public class LottoResult {
    private final Map<Rank, Integer> rankCounts;

    public LottoResult(Map<Rank, Integer> rankCounts) {
        this.rankCounts = new EnumMap<>(Rank.class);
        this.rankCounts.putAll(rankCounts);
        initializeAllRanks();
    }

    private void initializeAllRanks() {
        for (Rank rank : Rank.values()) {
            if (rank != Rank.NONE) {
                rankCounts.putIfAbsent(rank, 0);
            }
        }
    }

    public int getTotalPrize() {
        return rankCounts.entrySet().stream()
                .mapToInt(entry -> entry.getKey().getPrize() * entry.getValue())
                .sum();
    }

    public int getCountByRank(Rank rank) {
        return rankCounts.getOrDefault(rank, 0);
    }

    public Map<Rank, Integer> getRankCounts() {
        return Map.copyOf(rankCounts);
    }
}
