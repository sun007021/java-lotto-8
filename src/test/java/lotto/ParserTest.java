package lotto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
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

    @DisplayName("당첨 번호를 정상적으로 파싱한다")
    @Test
    void 당첨_번호를_정상적으로_파싱한다() {
        // given & when
        List<LottoNumber> numbers = Parser.parseWinningNumbers("1,2,3,4,5,6");

        // then
        assertThat(numbers).hasSize(6);
        assertThat(numbers.get(0).getValue()).isEqualTo(1);
        assertThat(numbers.get(5).getValue()).isEqualTo(6);
    }

    @DisplayName("당첨 번호 사이 공백을 제거하고 파싱한다")
    @Test
    void 당첨_번호_사이_공백을_제거하고_파싱한다() {
        // given & when
        List<LottoNumber> numbers = Parser.parseWinningNumbers("1, 2, 3, 4, 5, 6");

        // then
        assertThat(numbers).hasSize(6);
    }

    @DisplayName("당첨 번호 입력에 잘못된 형식이 있으면 예외가 발생한다")
    @Test
    void 당첨_번호_입력_형식_오류() {
        // given & when & then
        assertThatThrownBy(() -> Parser.parseWinningNumbers("1,2,3,a,5,6"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @DisplayName("보너스 번호를 정상적으로 파싱한다")
    @Test
    void 보너스_번호를_정상적으로_파싱한다() {
        // given & when
        LottoNumber bonus = Parser.parseBonusNumber("7");

        // then
        assertThat(bonus.getValue()).isEqualTo(7);
    }

    @DisplayName("보너스 번호에 공백이 있어도 파싱한다")
    @Test
    void 보너스_번호_공백_제거() {
        // given & when
        LottoNumber bonus = Parser.parseBonusNumber("  7  ");

        // then
        assertThat(bonus.getValue()).isEqualTo(7);
    }

    @DisplayName("보너스 번호 입력이 잘못되면 예외가 발생한다")
    @Test
    void 보너스_번호_입력_오류() {
        // given & when & then
        assertThatThrownBy(() -> Parser.parseBonusNumber("abc"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }
}