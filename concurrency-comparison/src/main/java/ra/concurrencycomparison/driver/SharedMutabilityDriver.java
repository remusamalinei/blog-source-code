package ra.concurrencycomparison.driver;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ra.concurrencycomparison.Adder;
import ra.concurrencycomparison.Constants;
import ra.concurrencycomparison.SharedMutabilityAdder;

/**
 * @author Remus Amalinei
 */
public class SharedMutabilityDriver {

    public static void main(String[] args) throws InterruptedException {
        int producerThreadCount = Integer.parseInt(args[0]);

        ExecutorService executorService = Executors.newFixedThreadPool(producerThreadCount);
        CountDownLatch countDownLatch = new CountDownLatch(producerThreadCount);

        int sliceSize = Constants.WORD_TO_GENERATE_COUNT / producerThreadCount;

        Adder adder = new SharedMutabilityAdder();
        int mapSize = Constants.TEN_WORD_MAP.size();
        for (int i = 0; i < producerThreadCount; i++) {
            final int from = i * sliceSize;
            final int to = calculateTo(i, producerThreadCount, sliceSize);

            Runnable task = () -> {
                Random random = new Random();
                for (int j = from; j < to; j++) {
                    int randomKey = random.nextInt(mapSize);
                    String word = Constants.TEN_WORD_MAP.get(randomKey);

                    adder.add(word);
                }
                countDownLatch.countDown();
            };

            executorService.execute(task);
        }

        executorService.shutdown();
        countDownLatch.await();

        adder.computeSum();
    }

    private static int calculateTo(int currentStep, int finalExclusiveStep, int sliceSize) {
        if ( ! lastStep(currentStep, finalExclusiveStep)) {
            return (currentStep + 1) * sliceSize;
        } else {
            return Constants.WORD_TO_GENERATE_COUNT;
        }
    }

    private static boolean lastStep(int currentStep, int finalExclusiveStep) {
        return currentStep == (finalExclusiveStep - 1);
    }
}
