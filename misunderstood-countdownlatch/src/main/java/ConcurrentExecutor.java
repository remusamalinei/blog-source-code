import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Remus Amalinei
 */
public class ConcurrentExecutor {

    private final static ExecutorService EXECUTOR_SERVICE = Executors.newCachedThreadPool();

    public Map<Integer, Object> executeAllCommands(Set<Integer> inputKeySet) throws InterruptedException {
        // not thread-safe if HashMap is used
        // Map<Integer, Object> resultMap = new HashMap<>();
        Map<Integer, Object> resultMap = new ConcurrentHashMap<>();

        CountDownLatch allDoneLatch = new CountDownLatch(inputKeySet.size());

        for (Integer key : inputKeySet) {
            Runnable runnable = () -> {
                Object resultOfAnImaginaryServiceCall = new Object();
                resultMap.put(key, resultOfAnImaginaryServiceCall);

                allDoneLatch.countDown();
            };

            EXECUTOR_SERVICE.execute(runnable);
        }

        allDoneLatch.await();

        return resultMap;
    }
}
