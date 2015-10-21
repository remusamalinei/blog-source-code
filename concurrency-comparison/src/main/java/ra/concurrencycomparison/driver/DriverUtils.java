package ra.concurrencycomparison.driver;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ra.concurrencycomparison.Adder;

import static ra.concurrencycomparison.Constants.TEN_WORD_MAP;
import static ra.concurrencycomparison.Constants.WORD_TO_GENERATE_COUNT;

/**
 * @author Remus Amalinei
 */
public class DriverUtils {

    private DriverUtils() {
        throw new AssertionError("utils class, not intended to be instantiated");
    }

    public static void addInCurrentThread(Adder adder) throws InterruptedException {
        Random random = new Random();

        int mapSize = TEN_WORD_MAP.size();
        for (int i = 0; i < WORD_TO_GENERATE_COUNT; i++) {
            int randomKey = random.nextInt(mapSize);
            String word = TEN_WORD_MAP.get(randomKey);

            adder.add(word);
        }
    }

    public static void addConcurrently(Adder adder, int threadCount) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        int sliceSize = WORD_TO_GENERATE_COUNT / threadCount;

        int mapSize = TEN_WORD_MAP.size();
        for (int i = 0; i < threadCount; i++) {
            final int from = i * sliceSize;
            final int to = calculateTo(i, threadCount, sliceSize);

            Runnable task = () -> {
                Random random = new Random();
                for (int j = from; j < to; j++) {
                    int randomKey = random.nextInt(mapSize);
                    String word = TEN_WORD_MAP.get(randomKey);

                    adder.add(word);
                }
                countDownLatch.countDown();
            };

            executorService.execute(task);
        }

        executorService.shutdown();
        countDownLatch.await();
    }

    private static int calculateTo(int currentStep, int finalExclusiveStep, int sliceSize) {
        if (!lastStep(currentStep, finalExclusiveStep)) {
            return (currentStep + 1) * sliceSize;
        } else {
            return WORD_TO_GENERATE_COUNT;
        }
    }

    private static boolean lastStep(int currentStep, int finalExclusiveStep) {
        return currentStep == (finalExclusiveStep - 1);
    }
}
