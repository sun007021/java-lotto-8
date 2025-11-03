package lotto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ParserTest {

    @DisplayName("구입 금액을 정상적으로 파싱한다")
    @Test
    void 구입_금액을_정상적으로_파싱한다() {
        // given & when & then
        assertThat(Parser.parsePurchaseAmount("1000")).isEqualTo(1000);
        assertThat(Parser.parsePurchaseAmount("5000")).isEqualTo(5000);
        assertThat(Parser.parsePurchaseAmount("10000")).isEqualTo(10000);
    }

    @DisplayName("구입 금액에서 단위를 제거하고 파싱한다")
    @Test
    void 구입_금액에서_단위를_제거하고_파싱한다() {
        // given & when & then
        assertThat(Parser.parsePurchaseAmount("1000원")).isEqualTo(1000);
        assertThat(Parser.parsePurchaseAmount("5000원")).isEqualTo(5000);
    }

    @DisplayName("공백이 포함된 구입 금액을 파싱한다")
    @Test
    void 공백이_포함된_구입_금액을_파싱한다() {
        // given & when & then
        assertThat(Parser.parsePurchaseAmount("  1000  ")).isEqualTo(1000);
        assertThat(Parser.parsePurchaseAmount(" 5000원 ")).isEqualTo(5000);
    }

    @DisplayName("숫자가 아닌 입력은 예외가 발생한다")
    @Test
    void 숫자가_아닌_입력은_예외가_발생한다() {
        // given & when & then
        assertThatThrownBy(() -> Parser.parsePurchaseAmount("abc"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]")
                .hasMessageContaining("숫자 형식");
    }

    @DisplayName("빈 문자열 입력은 예외가 발생한다")
    @Test
    void 빈_문자열_입력은_예외가_발생한다() {
        // given & when & then
        assertThatThrownBy(() -> Parser.parsePurchaseAmount(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @DisplayName("오버플로우 발생 시 예외가 발생한다")
    @Test
    void 오버플로우_발생_시_예외가_발생한다() {
        // given
        String overflowValue = "9999999999999";

        // when & then
        assertThatThrownBy(() -> Parser.parsePurchaseAmount(overflowValue))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]")
                .hasMessageContaining("너무 큽니다");
    }
}