package ra.concurrencycomparison.driver;

import java.util.Map;

import ra.concurrencycomparison.Adder;
import ra.concurrencycomparison.IsolatedMutabilityAdder;

import static ra.concurrencycomparison.Constants.POISON_PILL;

/**
 * @author Remus Amalinei
 */
public class IsolatedMutabilityDriver {

    public static void main(String[] args) throws InterruptedException {
        int producerThreadCount = Integer.parseInt(args[0]);
        int consumerThreadCount = Integer.parseInt(args[1]);

        long start = System.currentTimeMillis();
        Adder adder = new IsolatedMutabilityAdder(consumerThreadCount);

        DriverUtils.addConcurrently(adder, producerThreadCount);

        for (int i = 0; i < consumerThreadCount; i++) {
            adder.add(POISON_PILL);
        }

        Map<String, Integer> result = adder.computeSum();

        long duration = System.currentTimeMillis() - start;

        System.out.println("[IsolatedMutabilityDriver] " + duration + " ms");
        System.out.println("[IsolatedMutabilityDriver] " + result);
    }
}
