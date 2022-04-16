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
        long start = System.currentTimeMillis();

        Adder adder = new NotThreadSafeAdder();

        DriverUtils.addInCurrentThread(adder);

        Map<String, Integer> result = adder.computeSum();

        long duration = System.currentTimeMillis() - start;

        System.out.println("[SingleThreadDriver] " + duration + " ms");
        System.out.println("[SingleThreadDriver] " + result);
    }
}
