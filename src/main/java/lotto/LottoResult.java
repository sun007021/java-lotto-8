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

    public long getTotalPrize() {
        return rankCounts.entrySet().stream()
                .mapToLong(entry -> (long) entry.getKey().getPrize() * entry.getValue())
                .sum();
    }

    public double calculateReturnRate(int purchaseAmount) {
        long totalPrize = getTotalPrize();
        return (double) totalPrize / purchaseAmount * 100;
    }

    public int getCountByRank(Rank rank) {
        return rankCounts.getOrDefault(rank, 0);
    }

    public Map<Rank, Integer> getRankCounts() {
        return Map.copyOf(rankCounts);
    }
}
