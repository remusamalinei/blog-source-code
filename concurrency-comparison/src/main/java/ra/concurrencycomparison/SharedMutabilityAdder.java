package ra.concurrencycomparison;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Remus Amalinei
 */
public class SharedMutabilityAdder implements Adder {

    private ConcurrentMap<String, Integer> sumConcurrentMap = new ConcurrentHashMap<>();

    @Override
    public void add(String word) {
        while (true) {
            Integer previousValue = sumConcurrentMap.putIfAbsent(word, 1);

            if (previousValue != null) {
                int currentValue = sumConcurrentMap.get(word);
                int newValue = currentValue + 1;
                boolean replaced = sumConcurrentMap.replace(word, currentValue, newValue);
                if (replaced) {
                    break;
                }
            } else {
                break;
            }
        }
    }

    @Override
    public Map<String, Integer> computeSum() {
        return sumConcurrentMap;
    }
}
