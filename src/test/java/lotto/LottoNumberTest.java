package lotto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LottoNumberTest {

    @DisplayName("1부터 45 사이의 숫자로 로또 번호를 생성한다")
    @Test
    void 정상_범위_로또_번호_생성() {
        // given & when & then
        assertThat(new LottoNumber(1).getValue()).isEqualTo(1);
        assertThat(new LottoNumber(45).getValue()).isEqualTo(45);
        assertThat(new LottoNumber(23).getValue()).isEqualTo(23);
    }

    @DisplayName("1보다 작은 숫자는 예외가 발생한다")
    @Test
    void 최소값_미만_예외() {
        // given & when & then
        assertThatThrownBy(() -> new LottoNumber(0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]")
                .hasMessageContaining("1부터 45 사이");
    }

    @DisplayName("45보다 큰 숫자는 예외가 발생한다")
    @Test
    void 최대값_초과_예외() {
        // given & when & then
        assertThatThrownBy(() -> new LottoNumber(46))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]")
                .hasMessageContaining("1부터 45 사이");
    }

    @DisplayName("음수는 예외가 발생한다")
    @Test
    void 음수_예외() {
        // given & when & then
        assertThatThrownBy(() -> new LottoNumber(-1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @DisplayName("같은 값을 가진 로또 번호는 동등하다")
    @Test
    void 동등성_테스트() {
        // given
        LottoNumber number1 = new LottoNumber(10);
        LottoNumber number2 = new LottoNumber(10);
        LottoNumber number3 = new LottoNumber(20);

        // when & then
        assertThat(number1).isEqualTo(number2);
        assertThat(number1).isNotEqualTo(number3);
        assertThat(number1.hashCode()).isEqualTo(number2.hashCode());
    }

    @DisplayName("toString은 숫자를 문자열로 반환한다")
    @Test
    void toString_테스트() {
        // given
        LottoNumber number = new LottoNumber(23);

        // when & then
        assertThat(number.toString()).isEqualTo("23");
    }
}