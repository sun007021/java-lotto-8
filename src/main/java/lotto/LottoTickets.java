package lotto;

import java.util.ArrayList;
import java.util.List;

public class LottoTickets {
    private final List<Lotto> lottos;

    public LottoTickets(List<Lotto> lottos) {
        this.lottos = new ArrayList<>(lottos);
    }

    public int getCount() {
        return lottos.size();
    }

    public List<List<Integer>> getAllLottoNumbers() {
        return lottos.stream()
                .map(Lotto::getNumbers)
                .toList();
    }
}