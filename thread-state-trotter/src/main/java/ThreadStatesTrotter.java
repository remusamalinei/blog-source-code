/**
 * @author Remus Amalinei
 */
public class ThreadStatesTrotter {

    public static void main(String[] args) throws InterruptedException {

        Thread trotterThread = constructTrotterThread();

        printStatus("1 - constructed but not yet started", trotterThread);

        trotterThread.start();
        trotterThread.join(500);
        printStatus("3 - waiting for delayerThread to terminate", trotterThread);

        trotterThread.join(1000);
        printStatus("5 - sleeping", trotterThread);

        Thread blockerThread = new Thread(() -> block());
        blockerThread.start();

        trotterThread.join(1000);
        printStatus("7 - trying to acquire lock", trotterThread);

        trotterThread.join();
        printStatus("9 - journey completed", trotterThread);
    }

    private static Thread constructTrotterThread() {
        Runnable runnable = () -> {
            printStatus("2 - started to execute", Thread.currentThread());

            Thread delayerThread = new Thread(() -> sleep1Second());

            delayerThread.start();
            try {
                delayerThread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            printStatus("4 - delayerThread terminated, running again", Thread.currentThread());

            sleep1Second();

            printStatus("6 - exited from sleep, running again", Thread.currentThread());

            block();
        };

        Thread thread = new Thread(runnable);
        thread.setName("trotterThread");

        return thread;
    }

    private static void printStatus(String message, Thread thread) {
        System.out.printf("%-54s - thread " + thread.getName() + " is in state " + thread.getState() + "\n",
                message);
    }

    private static synchronized void block() {
        if ("trotterThread".equals(Thread.currentThread().getName())) {
            printStatus("8 - lock acquired, running inside the critical section", Thread.currentThread());
        } else {
            sleep1Second();
        }
    }

    private static void sleep1Second() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
