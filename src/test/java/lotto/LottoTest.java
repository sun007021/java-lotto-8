package lotto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LottoTest {

    private List<LottoNumber> createLottoNumbers(int... numbers) {
        return Arrays.stream(numbers)
                .mapToObj(LottoNumber::new)
                .collect(Collectors.toList());
    }

    @Test
    void 로또_번호의_개수가_6개가_넘어가면_예외가_발생한다() {
        assertThatThrownBy(() -> new Lotto(createLottoNumbers(1, 2, 3, 4, 5, 6, 7)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("로또 번호에 중복된 숫자가 있으면 예외가 발생한다.")
    @Test
    void 로또_번호에_중복된_숫자가_있으면_예외가_발생한다() {
        assertThatThrownBy(() -> new Lotto(createLottoNumbers(1, 2, 3, 4, 5, 5)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("로또 번호의 개수가 6개보다 작으면 예외가 발생한다.")
    @Test
    void 로또_번호의_개수가_6개복다_작으면_예외가_발생한다() {
        assertThatThrownBy(() -> new Lotto(createLottoNumbers(1, 2, 3, 4, 5)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("로또 번호가 1~45 범위를 벗어나면 예외가 발생한다.")
    @Test
    void 로또_번호가_1_45_범위를_벗어나면_예외가_발생한다() {
        assertThatThrownBy(() -> createLottoNumbers(0, 2, 3, 4, 5, 6))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> createLottoNumbers(1, 2, 3, 4, 5, 46))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("로또 번호에 음수가 있으면 예외가 발생한다.")
    @Test
    void 로또_번호에_음수가_있으면_예외가_발생한다() {
        assertThatThrownBy(() -> createLottoNumbers(-1, 2, 3, 4, 5, 6))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("로또 번호가 정상 범위 내에 있으면 예외가 발생하지 않는다.")
    @Test
    void 로또_번호가_정상_범위_내에_있으면_예외가_발생하지_않는다() {
        new Lotto(createLottoNumbers(1, 15, 23, 34, 42, 45));
    }

    @DisplayName("두 로또의 일치하는 번호 개수를 반환한다.")
    @Test
    void 두_로또의_일치하는_번호_개수를_반환한다() {
        // given
        Lotto lotto1 = new Lotto(createLottoNumbers(1, 2, 3, 4, 5, 6));
        Lotto lotto2 = new Lotto(createLottoNumbers(1, 2, 3, 7, 8, 9));

        // when
        int matchCount = lotto1.countMatches(lotto2);

        // then
        assertThat(matchCount).isEqualTo(3);
    }

    @DisplayName("완전히 일치하는 경우 6개를 반환한다.")
    @Test
    void 완전히_일치하는_경우_6개를_반환한다() {
        // given
        Lotto lotto1 = new Lotto(createLottoNumbers(1, 2, 3, 4, 5, 6));
        Lotto lotto2 = new Lotto(createLottoNumbers(1, 2, 3, 4, 5, 6));

        // when
        int matchCount = lotto1.countMatches(lotto2);

        // then
        assertThat(matchCount).isEqualTo(6);
    }

    @DisplayName("일치하는 번호가 없으면 0을 반환한다.")
    @Test
    void 일치하는_번호가_없으면_0을_반환한다() {
        // given
        Lotto lotto1 = new Lotto(createLottoNumbers(1, 2, 3, 4, 5, 6));
        Lotto lotto2 = new Lotto(createLottoNumbers(7, 8, 9, 10, 11, 12));

        // when
        int matchCount = lotto1.countMatches(lotto2);

        // then
        assertThat(matchCount).isEqualTo(0);
    }
}
