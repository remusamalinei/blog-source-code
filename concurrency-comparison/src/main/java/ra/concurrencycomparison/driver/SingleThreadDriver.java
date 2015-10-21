package ra.concurrencycomparison.driver;

import java.util.Map;
import java.util.Random;

import ra.concurrencycomparison.Adder;
import ra.concurrencycomparison.Constants;
import ra.concurrencycomparison.NotThreadSafeAdder;

/**
 * @author Remus Amalinei
 */
public class SingleThreadDriver {

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();

        Adder adder = new NotThreadSafeAdder();
        int mapSize = Constants.TEN_WORD_MAP.size();
        for (int i = 0; i < Constants.WORD_TO_GENERATE_COUNT; i++) {
            int randomKey = random.nextInt(mapSize);
            String word = Constants.TEN_WORD_MAP.get(randomKey);

            adder.add(word);
        }

        Map<String, Integer> resultMap = adder.computeSum();
        System.out.println(resultMap);
    }
}
