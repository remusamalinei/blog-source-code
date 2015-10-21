package ra.concurrencycomparison;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Remus Amalinei
 */
public class NotThreadSafeAdder implements Adder {

    private Map<String, Integer> sumMap = new HashMap<>();

    @Override
    public void add(String word) {
        if (sumMap.containsKey(word)) {
            int currentValue = sumMap.get(word);
            int newValue = currentValue + 1;

            sumMap.put(word, newValue);
        } else {
            sumMap.put(word, 1);
        }
    }

    @Override
    public Map<String, Integer> computeSum() {
        return new HashMap<>(sumMap);
    }
}
