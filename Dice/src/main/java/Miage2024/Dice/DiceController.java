package Miage2024.Dice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
public class DiceController {

    @Autowired
    private DiceRepository diceRepository;

    @Autowired
    private Dice dice;

    @GetMapping("/rollDice")
    public int rollDice() {
        int result = dice.roll();
        saveDiceRollLog(1, List.of(result));
        return result;
    }

    @GetMapping("/rollDice/{X}")
    public int[] rollDices(@PathVariable int X) {
        List<Integer> resultList = new ArrayList<>();
        int[] results = new int[X];
        for (int i = 0; i < X; i++) {
            results[i] = dice.roll();
            resultList.add(results[i]);
        }
        saveDiceRollLog(X, resultList);
        return results;
    }

    @GetMapping("/diceLogs")
    public List<DiceRollLog> getDiceLogs() {
        return diceRepository.findAll();
    }

    private void saveDiceRollLog(int diceCount, List<Integer> results) {
        DiceRollLog log = new DiceRollLog();
        log.setDiceCount(diceCount);
        log.setResults(results);
        log.setTimestamp(LocalDateTime.now());
        diceRepository.save(log);
    }
}
