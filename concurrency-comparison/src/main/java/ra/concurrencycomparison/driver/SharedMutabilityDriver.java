package ra.concurrencycomparison.driver;

import java.util.Map;

import ra.concurrencycomparison.Adder;
import ra.concurrencycomparison.SharedMutabilityAdder;

/**
 * @author Remus Amalinei
 */
public class SharedMutabilityDriver {

    public static void main(String[] args) throws InterruptedException {
        int producerThreadCount = Integer.parseInt(args[0]);

        long start = System.currentTimeMillis();

        Adder adder = new SharedMutabilityAdder();

        DriverUtils.addConcurrently(adder, producerThreadCount);

        Map<String, Integer> result = adder.computeSum();

        long duration = System.currentTimeMillis() - start;

        System.out.println("[SharedMutabilityDriver] " + duration + " ms");
        System.out.println("[SharedMutabilityDriver] " + result);
    }
}
