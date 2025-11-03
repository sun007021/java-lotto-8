package lotto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class LottoTickets {
    private final List<Lotto> lottos;

    public LottoTickets(List<Lotto> lottos) {
        this.lottos = new ArrayList<>(lottos);
    }

    public int getCount() {
        return lottos.size();
    }

    public List<Lotto> getLottos() {
        return Collections.unmodifiableList(lottos);
    }

    public List<List<Integer>> getAllLottoNumbers() {
        return lottos.stream()
                .map(Lotto::getNumbers)
                .collect(Collectors.toList());
    }
}