package lotto;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LottoTicketsTest {

    private List<LottoNumber> createLottoNumbers(int... numbers) {
        return Arrays.stream(numbers)
                .mapToObj(LottoNumber::new)
                .collect(Collectors.toList());
    }

    @DisplayName("구매한 로또 개수를 반환한다")
    @Test
    void 구매한_로또_개수() {
        // given
        List<Lotto> lottos = List.of(
                new Lotto(createLottoNumbers(1, 2, 3, 4, 5, 6)),
                new Lotto(createLottoNumbers(7, 8, 9, 10, 11, 12))
        );
        LottoTickets tickets = new LottoTickets(lottos);

        // when & then
        assertThat(tickets.getCount()).isEqualTo(2);
    }

    @DisplayName("단일 로또의 당첨 결과를 집계한다")
    @Test
    void 단일_로또_당첨_결과() {
        // given
        List<Lotto> lottos = List.of(
                new Lotto(createLottoNumbers(1, 2, 3, 4, 5, 6))
        );
        LottoTickets tickets = new LottoTickets(lottos);

        Lotto winningLotto = new Lotto(createLottoNumbers(1, 2, 3, 4, 5, 6));
        WinningNumbers winningNumbers = new WinningNumbers(winningLotto, new LottoNumber(7));

        // when
        LottoResult result = tickets.checkResults(winningNumbers);

        // then
        assertThat(result.getCountByRank(Rank.FIRST)).isEqualTo(1);
        assertThat(result.getCountByRank(Rank.SECOND)).isEqualTo(0);
    }

    @DisplayName("여러 로또의 혼합 당첨 결과를 집계한다")
    @Test
    void 여러_로또_혼합_당첨_결과() {
        // given
        List<Lotto> lottos = List.of(
                new Lotto(createLottoNumbers(1, 2, 3, 4, 5, 6)),      // 1등 (6개)
                new Lotto(createLottoNumbers(1, 2, 3, 4, 5, 7)),      // 2등 (5개 + 보너스)
                new Lotto(createLottoNumbers(1, 2, 3, 4, 5, 8)),      // 3등 (5개)
                new Lotto(createLottoNumbers(1, 2, 3, 4, 10, 11)),    // 4등 (4개)
                new Lotto(createLottoNumbers(1, 2, 3, 10, 11, 12))    // 5등 (3개)
        );
        LottoTickets tickets = new LottoTickets(lottos);

        Lotto winningLotto = new Lotto(createLottoNumbers(1, 2, 3, 4, 5, 6));
        WinningNumbers winningNumbers = new WinningNumbers(winningLotto, new LottoNumber(7));

        // when
        LottoResult result = tickets.checkResults(winningNumbers);

        // then
        assertThat(result.getCountByRank(Rank.FIRST)).isEqualTo(1);
        assertThat(result.getCountByRank(Rank.SECOND)).isEqualTo(1);
        assertThat(result.getCountByRank(Rank.THIRD)).isEqualTo(1);
        assertThat(result.getCountByRank(Rank.FOURTH)).isEqualTo(1);
        assertThat(result.getCountByRank(Rank.FIFTH)).isEqualTo(1);
    }

    @DisplayName("동일 등수가 여러 개 있으면 정확히 집계한다")
    @Test
    void 동일_등수_여러개_집계() {
        // given
        List<Lotto> lottos = List.of(
                new Lotto(createLottoNumbers(1, 2, 3, 10, 11, 12)),    // 5등 (3개)
                new Lotto(createLottoNumbers(1, 2, 3, 13, 14, 15)),    // 5등 (3개)
                new Lotto(createLottoNumbers(1, 2, 3, 16, 17, 18))     // 5등 (3개)
        );
        LottoTickets tickets = new LottoTickets(lottos);

        Lotto winningLotto = new Lotto(createLottoNumbers(1, 2, 3, 4, 5, 6));
        WinningNumbers winningNumbers = new WinningNumbers(winningLotto, new LottoNumber(7));

        // when
        LottoResult result = tickets.checkResults(winningNumbers);

        // then
        assertThat(result.getCountByRank(Rank.FIFTH)).isEqualTo(3);
        assertThat(result.getCountByRank(Rank.FIRST)).isEqualTo(0);
    }

    @DisplayName("전부 낙첨이면 모든 등수가 0개이다")
    @Test
    void 전부_낙첨() {
        // given
        List<Lotto> lottos = List.of(
                new Lotto(createLottoNumbers(10, 11, 12, 13, 14, 15)),
                new Lotto(createLottoNumbers(20, 21, 22, 23, 24, 25))
        );
        LottoTickets tickets = new LottoTickets(lottos);

        Lotto winningLotto = new Lotto(createLottoNumbers(1, 2, 3, 4, 5, 6));
        WinningNumbers winningNumbers = new WinningNumbers(winningLotto, new LottoNumber(7));

        // when
        LottoResult result = tickets.checkResults(winningNumbers);

        // then
        assertThat(result.getCountByRank(Rank.FIRST)).isEqualTo(0);
        assertThat(result.getCountByRank(Rank.FIFTH)).isEqualTo(0);
        assertThat(result.getTotalPrize()).isEqualTo(0);
    }
}