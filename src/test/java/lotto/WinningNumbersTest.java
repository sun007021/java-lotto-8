package lotto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class WinningNumbersTest {

    private List<LottoNumber> createLottoNumbers(int... numbers) {
        return Arrays.stream(numbers)
                .mapToObj(LottoNumber::new)
                .collect(Collectors.toList());
    }

    @DisplayName("정상적인 당첨 번호와 보너스 번호로 생성한다")
    @Test
    void 정상_생성() {
        // given
        Lotto winningLotto = new Lotto(createLottoNumbers(1, 2, 3, 4, 5, 6));
        LottoNumber bonus = new LottoNumber(7);

        // when
        WinningNumbers winningNumbers = new WinningNumbers(winningLotto, bonus);

        // then
        assertThat(winningNumbers.getWinningNumbers()).hasSize(6);
        assertThat(winningNumbers.getBonusNumber().getValue()).isEqualTo(7);
    }

    @DisplayName("보너스 번호가 당첨 번호와 중복되면 예외가 발생한다")
    @Test
    void 보너스_번호_중복_예외() {
        // given
        Lotto winningLotto = new Lotto(createLottoNumbers(1, 2, 3, 4, 5, 6));
        LottoNumber bonus = new LottoNumber(6);  // 중복

        // when & then
        assertThatThrownBy(() -> new WinningNumbers(winningLotto, bonus))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]")
                .hasMessageContaining("보너스 번호는 당첨 번호와 중복될 수 없습니다");
    }

    @DisplayName("당첨 번호가 6개가 아니면 예외가 발생한다")
    @Test
    void 당첨_번호_개수_예외() {
        // given
        List<LottoNumber> numbers = createLottoNumbers(1, 2, 3, 4, 5);

        // when & then
        assertThatThrownBy(() -> new Lotto(numbers))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]")
                .hasMessageContaining("6개");
    }

    @DisplayName("당첨 번호에 중복이 있으면 예외가 발생한다")
    @Test
    void 당첨_번호_중복_예외() {
        // given
        List<LottoNumber> numbers = createLottoNumbers(1, 2, 3, 4, 5, 5);

        // when & then
        assertThatThrownBy(() -> new Lotto(numbers))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]")
                .hasMessageContaining("중복");
    }

    @DisplayName("보너스 번호가 1~45 범위를 벗어나면 예외가 발생한다")
    @Test
    void 보너스_번호_범위_예외() {
        // when & then
        assertThatThrownBy(() -> new LottoNumber(46))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new LottoNumber(0))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("당첨 번호는 정렬된 상태로 반환된다")
    @Test
    void 당첨_번호_정렬() {
        // given
        Lotto winningLotto = new Lotto(createLottoNumbers(6, 3, 1, 5, 2, 4));
        LottoNumber bonus = new LottoNumber(7);

        // when
        WinningNumbers winningNumbers = new WinningNumbers(winningLotto, bonus);
        List<LottoNumber> result = winningNumbers.getWinningNumbers();

        // then
        assertThat(result.get(0).getValue()).isEqualTo(1);
        assertThat(result.get(5).getValue()).isEqualTo(6);
    }

    @DisplayName("6개 일치하면 1등이다")
    @Test
    void 일등_판정() {
        // given
        Lotto winningLotto = new Lotto(createLottoNumbers(1, 2, 3, 4, 5, 6));
        WinningNumbers winningNumbers = new WinningNumbers(winningLotto, new LottoNumber(7));
        Lotto purchasedLotto = new Lotto(createLottoNumbers(1, 2, 3, 4, 5, 6));

        // when
        Rank rank = winningNumbers.checkRank(purchasedLotto);

        // then
        assertThat(rank).isEqualTo(Rank.FIRST);
    }

    @DisplayName("5개 일치하고 보너스가 일치하면 2등이다")
    @Test
    void 이등_판정() {
        // given
        Lotto winningLotto = new Lotto(createLottoNumbers(1, 2, 3, 4, 5, 6));
        WinningNumbers winningNumbers = new WinningNumbers(winningLotto, new LottoNumber(7));
        Lotto purchasedLotto = new Lotto(createLottoNumbers(1, 2, 3, 4, 5, 7));

        // when
        Rank rank = winningNumbers.checkRank(purchasedLotto);

        // then
        assertThat(rank).isEqualTo(Rank.SECOND);
    }

    @DisplayName("5개 일치하고 보너스가 불일치하면 3등이다")
    @Test
    void 삼등_판정() {
        // given
        Lotto winningLotto = new Lotto(createLottoNumbers(1, 2, 3, 4, 5, 6));
        WinningNumbers winningNumbers = new WinningNumbers(winningLotto, new LottoNumber(7));
        Lotto purchasedLotto = new Lotto(createLottoNumbers(1, 2, 3, 4, 5, 8));

        // when
        Rank rank = winningNumbers.checkRank(purchasedLotto);

        // then
        assertThat(rank).isEqualTo(Rank.THIRD);
    }

    @DisplayName("4개 일치하면 4등이다")
    @Test
    void 사등_판정() {
        // given
        Lotto winningLotto = new Lotto(createLottoNumbers(1, 2, 3, 4, 5, 6));
        WinningNumbers winningNumbers = new WinningNumbers(winningLotto, new LottoNumber(7));
        Lotto purchasedLotto = new Lotto(createLottoNumbers(1, 2, 3, 4, 8, 9));

        // when
        Rank rank = winningNumbers.checkRank(purchasedLotto);

        // then
        assertThat(rank).isEqualTo(Rank.FOURTH);
    }

    @DisplayName("3개 일치하면 5등이다")
    @Test
    void 오등_판정() {
        // given
        Lotto winningLotto = new Lotto(createLottoNumbers(1, 2, 3, 4, 5, 6));
        WinningNumbers winningNumbers = new WinningNumbers(winningLotto, new LottoNumber(7));
        Lotto purchasedLotto = new Lotto(createLottoNumbers(1, 2, 3, 8, 9, 10));

        // when
        Rank rank = winningNumbers.checkRank(purchasedLotto);

        // then
        assertThat(rank).isEqualTo(Rank.FIFTH);
    }

    @DisplayName("2개 이하 일치하면 낙첨이다")
    @Test
    void 낙첨_판정() {
        // given
        Lotto winningLotto = new Lotto(createLottoNumbers(1, 2, 3, 4, 5, 6));
        WinningNumbers winningNumbers = new WinningNumbers(winningLotto, new LottoNumber(7));
        Lotto purchasedLotto = new Lotto(createLottoNumbers(1, 2, 8, 9, 10, 11));

        // when
        Rank rank = winningNumbers.checkRank(purchasedLotto);

        // then
        assertThat(rank).isEqualTo(Rank.NONE);
    }
}