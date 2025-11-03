package lotto;

import java.util.function.Supplier;
import lotto.error.ErrorHandler;

public class RetryHandler {

    public static <T> T retryOnException(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                ErrorHandler.handle(e);
            }
        }
    }
}
