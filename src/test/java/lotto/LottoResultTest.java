package lotto;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LottoResultTest {

    @DisplayName("총 상금을 정확히 계산한다")
    @Test
    void 총_상금_계산() {
        // given
        Map<Rank, Integer> rankCounts = Map.of(
                Rank.FIFTH, 2,    // 5,000 * 2 = 10,000
                Rank.FOURTH, 1    // 50,000 * 1 = 50,000
        );
        LottoResult result = new LottoResult(rankCounts);

        // when
        long totalPrize = result.getTotalPrize();

        // then
        assertThat(totalPrize).isEqualTo(60_000L);
    }

    @DisplayName("특정 등수의 당첨 개수를 조회한다")
    @Test
    void 등수별_개수_조회() {
        // given
        Map<Rank, Integer> rankCounts = Map.of(
                Rank.FIRST, 1,
                Rank.THIRD, 3
        );
        LottoResult result = new LottoResult(rankCounts);

        // when & then
        assertThat(result.getCountByRank(Rank.FIRST)).isEqualTo(1);
        assertThat(result.getCountByRank(Rank.THIRD)).isEqualTo(3);
    }

    @DisplayName("모든 등수가 0으로 초기화된다")
    @Test
    void 모든_등수_초기화() {
        // given
        LottoResult result = new LottoResult(Map.of());

        // when & then
        assertThat(result.getCountByRank(Rank.FIRST)).isEqualTo(0);
        assertThat(result.getCountByRank(Rank.SECOND)).isEqualTo(0);
        assertThat(result.getCountByRank(Rank.THIRD)).isEqualTo(0);
        assertThat(result.getCountByRank(Rank.FOURTH)).isEqualTo(0);
        assertThat(result.getCountByRank(Rank.FIFTH)).isEqualTo(0);
    }

    @DisplayName("당첨되지 않은 등수는 0개로 표시된다")
    @Test
    void 당첨되지_않은_등수는_0개() {
        // given
        Map<Rank, Integer> rankCounts = Map.of(Rank.FIRST, 1);
        LottoResult result = new LottoResult(rankCounts);

        // when & then
        assertThat(result.getCountByRank(Rank.SECOND)).isEqualTo(0);
        assertThat(result.getCountByRank(Rank.FIFTH)).isEqualTo(0);
    }

    @DisplayName("총 상금이 0일 때는 0을 반환한다")
    @Test
    void 총_상금_0일때() {
        // given
        LottoResult result = new LottoResult(Map.of());

        // when
        int totalPrize = result.getTotalPrize();

        // then
        assertThat(totalPrize).isEqualTo(0);
    }

    @DisplayName("여러 등수가 혼합된 경우 총 상금을 정확히 계산한다")
    @Test
    void 여러_등수_혼합_상금_계산() {
        // given
        Map<Rank, Integer> rankCounts = Map.of(
                Rank.FIRST, 1,     // 2,000,000,000
                Rank.SECOND, 1,    // 30,000,000
                Rank.THIRD, 2,     // 1,500,000 * 2 = 3,000,000
                Rank.FOURTH, 3,    // 50,000 * 3 = 150,000
                Rank.FIFTH, 5      // 5,000 * 5 = 25,000
        );
        LottoResult result = new LottoResult(rankCounts);

        // when
        int totalPrize = result.getTotalPrize();

        // then
        assertThat(totalPrize).isEqualTo(2_033_175_000);
    }

    @DisplayName("1등이 여러 장일 때 총 상금을 정확히 계산한다 (오버플로우 검증)")
    @Test
    void 일등_여러장_상금_계산() {
        // given
        Map<Rank, Integer> rankCounts = Map.of(
                Rank.FIRST, 3      // 2,000,000,000 * 3 = 6,000,000,000 (int 최대값 초과)
        );
        LottoResult result = new LottoResult(rankCounts);

        // when
        int totalPrize = result.getTotalPrize();

        // then
        assertThat(totalPrize).isEqualTo(6_000_000_000L);
    }

    @DisplayName("수익률을 정확히 계산한다")
    @Test
    void 수익률_계산() {
        // given
        Map<Rank, Integer> rankCounts = Map.of(Rank.FIFTH, 1);  // 5,000원
        LottoResult result = new LottoResult(rankCounts);

        // when
        double returnRate = result.calculateReturnRate(10_000);

        // then
        assertThat(returnRate).isEqualTo(50.0);
    }

    @DisplayName("수익률이 소수점일 때 정확히 계산한다")
    @Test
    void 수익률_소수점_계산() {
        // given
        Map<Rank, Integer> rankCounts = Map.of(Rank.FIFTH, 1);  // 5,000원
        LottoResult result = new LottoResult(rankCounts);

        // when
        double returnRate = result.calculateReturnRate(14_000);

        // then
        // 5,000 / 14,000 * 100 = 35.714...
        assertThat(returnRate).isCloseTo(35.714, org.assertj.core.data.Offset.offset(0.001));
    }
}