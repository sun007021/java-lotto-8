package lotto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LottoGameTest {

    @DisplayName("1000원 단위로 로또를 구매하면 정상적으로 생성된다")
    @Test
    void 천원_단위로_로또를_구매하면_정상적으로_생성된다() {
        // given & when
        LottoGame game = new LottoGame(1000);

        // then
        assertThat(game.getTickets()).isNotNull();
        assertThat(game.getTickets().getCount()).isEqualTo(1);
    }

    @DisplayName("여러 장의 로또를 구매할 수 있다")
    @Test
    void 여러_장의_로또를_구매할_수_있다() {
        // given & when
        LottoGame game = new LottoGame(5000);

        // then
        assertThat(game.getTickets().getCount()).isEqualTo(5);
    }

    @DisplayName("1000원 단위가 아니면 예외가 발생한다")
    @Test
    void 천원_단위가_아니면_예외가_발생한다() {
        // given & when & then
        assertThatThrownBy(() -> new LottoGame(1500))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]")
                .hasMessageContaining("1000원 단위");
    }

    @DisplayName("100원으로 구매하면 예외가 발생한다")
    @Test
    void 백원으로_구매하면_예외가_발생한다() {
        // given & when & then
        assertThatThrownBy(() -> new LottoGame(100))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }
}