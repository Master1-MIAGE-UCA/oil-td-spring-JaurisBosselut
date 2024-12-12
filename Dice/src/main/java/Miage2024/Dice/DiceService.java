package Miage2024.Dice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



@Service
public class DiceService {
    @Autowired
    private DiceRepository diceRepository;

    @Autowired
    private Dice dice;

    public List<Integer> rollDices(int diceCount) {
        List<Integer> results = new ArrayList<>();
        for (int i = 0; i < diceCount; i++) {
            results.add(dice.roll());
        }

        DiceRollLog log = new DiceRollLog();
        log.setDiceCount(diceCount);
        log.setResults(results);
        log.setTimestamp(LocalDateTime.now());
        diceRepository.save(log);

        return results;
    }
}
