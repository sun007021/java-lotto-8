package lotto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RankTest {

    @DisplayName("6개 일치하면 1등이다")
    @Test
    void 일등_판정() {
        // given & when
        Rank rank = Rank.valueOf(6, false);

        // then
        assertThat(rank).isEqualTo(Rank.FIRST);
        assertThat(rank.getPrize()).isEqualTo(2_000_000_000);
    }

    @DisplayName("5개 일치하고 보너스가 일치하면 2등이다")
    @Test
    void 이등_판정() {
        // given & when
        Rank rank = Rank.valueOf(5, true);

        // then
        assertThat(rank).isEqualTo(Rank.SECOND);
        assertThat(rank.getPrize()).isEqualTo(30_000_000);
    }

    @DisplayName("5개 일치하고 보너스가 불일치하면 3등이다")
    @Test
    void 삼등_판정() {
        // given & when
        Rank rank = Rank.valueOf(5, false);

        // then
        assertThat(rank).isEqualTo(Rank.THIRD);
        assertThat(rank.getPrize()).isEqualTo(1_500_000);
    }

    @DisplayName("4개 일치하면 4등이다")
    @Test
    void 사등_판정() {
        // given & when
        Rank rank = Rank.valueOf(4, false);

        // then
        assertThat(rank).isEqualTo(Rank.FOURTH);
        assertThat(rank.getPrize()).isEqualTo(50_000);
    }

    @DisplayName("3개 일치하면 5등이다")
    @Test
    void 오등_판정() {
        // given & when
        Rank rank = Rank.valueOf(3, false);

        // then
        assertThat(rank).isEqualTo(Rank.FIFTH);
        assertThat(rank.getPrize()).isEqualTo(5_000);
    }

    @DisplayName("2개 이하 일치하면 낙첨이다")
    @Test
    void 낙첨_판정() {
        // given & when & then
        assertThat(Rank.valueOf(2, false)).isEqualTo(Rank.NONE);
        assertThat(Rank.valueOf(1, false)).isEqualTo(Rank.NONE);
        assertThat(Rank.valueOf(0, false)).isEqualTo(Rank.NONE);
    }

    @DisplayName("낙첨 시 상금은 0원이다")
    @Test
    void 낙첨_상금() {
        // given & when
        Rank rank = Rank.valueOf(2, false);

        // then
        assertThat(rank.getPrize()).isEqualTo(0);
    }
}