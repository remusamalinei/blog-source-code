package ra.concurrencycomparison;

import java.util.Map;

/**
 * @author Remus Amalinei
 */
public interface Adder {

    void add(String word);

    Map<String, Integer> computeSum();
}
