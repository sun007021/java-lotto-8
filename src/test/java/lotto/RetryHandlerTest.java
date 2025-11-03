package lotto;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RetryHandlerTest {

    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        outputStream = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @DisplayName("예외 없이 정상 실행되면 결과를 반환한다")
    @Test
    void 예외_없이_정상_실행되면_결과를_반환한다() {
        // given
        String expected = "success";

        // when
        String result = RetryHandler.retryOnException(() -> expected);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @DisplayName("예외 발생 시 에러 메시지를 출력하고 재시도한다")
    @Test
    void 예외_발생_시_에러_메시지를_출력하고_재시도한다() {
        // given
        AtomicInteger attemptCount = new AtomicInteger(0);

        // when
        int result = RetryHandler.retryOnException(() -> {
            int count = attemptCount.incrementAndGet();
            if (count < 3) {
                throw new IllegalArgumentException("[ERROR] 잘못된 입력입니다.");
            }
            return count;
        });

        // then
        assertThat(result).isEqualTo(3);
        assertThat(attemptCount.get()).isEqualTo(3);
        String output = outputStream.toString();
        assertThat(output).contains("[ERROR] 잘못된 입력입니다.");
    }

    @DisplayName("여러 번 예외가 발생해도 계속 재시도한다")
    @Test
    void 여러_번_예외가_발생해도_계속_재시도한다() {
        // given
        AtomicInteger attemptCount = new AtomicInteger(0);

        // when
        int result = RetryHandler.retryOnException(() -> {
            int count = attemptCount.incrementAndGet();
            if (count <= 5) {
                throw new IllegalArgumentException("[ERROR] 시도 " + count);
            }
            return count;
        });

        // then
        assertThat(result).isEqualTo(6);
        assertThat(attemptCount.get()).isEqualTo(6);
    }
}