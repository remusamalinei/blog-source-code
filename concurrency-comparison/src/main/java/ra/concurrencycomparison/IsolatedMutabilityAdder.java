package ra.concurrencycomparison;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Remus Amalinei
 */
public class IsolatedMutabilityAdder implements Adder {

    private final ExecutorService executorService;
    private final BlockingQueue<String> mailboxBlockingQueue = new LinkedBlockingQueue<>();
    private final List<Future<Map<String, Integer>>> partialResultList;

    public IsolatedMutabilityAdder(int threadCount) {
        executorService = Executors.newFixedThreadPool(threadCount);
        partialResultList = new ArrayList<>();

        for (int i = 0; i < threadCount; i++) {
            partialResultList.add(executorService.submit(buildResultCallable()));
        }
    }

    private Callable<Map<String, Integer>> buildResultCallable() {
        return () -> {
            Adder adder = new NotThreadSafeAdder();

            while (true) {
                try {
                    String word = mailboxBlockingQueue.take();

                    if (Constants.POISON_PILL.equals(word)) {
                        break;
                    }

                    adder.add(word);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            return adder.computeSum();
        };
    }

    @Override
    public void add(String word) {
        try {
            mailboxBlockingQueue.put(word);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public Map<String, Integer> computeSum() {
        executorService.shutdown();
        Map<String, Integer> totalResult = new HashMap<>();

        for (Future<Map<String, Integer>> partialResult : partialResultList) {
            try {
                Map<String, Integer> result = partialResult.get();
                result.forEach((k, v) -> {
                    Integer existingValue;
                    if (totalResult.containsKey(k)) {
                        existingValue = totalResult.get(k);
                    } else {
                        existingValue = 0;
                    }

                    totalResult.put(k, existingValue + v);
                });
            } catch (InterruptedException | ExecutionException e) {
                Thread.currentThread().interrupt();
            }
        }

        return totalResult;
    }
}
