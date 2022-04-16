import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * Based on the code example presented in the JDK API documentation for {@link Condition}.
 *
 * @author JDK API Documentation (original author)
 * @author Remus Amalinei
 */
public class BoundedBuffer {

    final Lock lock;
    final Condition notFull;
    final Condition notEmpty;

    final Object[] items = new Object[100];
    int putptr, takeptr, count;

    public BoundedBuffer(Lock lock) {
        this.lock = lock;

        notFull = lock.newCondition();
        notEmpty = lock.newCondition();
    }

    public void put(Object x) throws InterruptedException {
        lock.lock();
        try {
            addToBuffer(x);
        } finally {
            lock.unlock();
        }
    }

    void addToBuffer(Object x) throws InterruptedException {
        while (count == items.length)
            notFull.await();
        items[putptr] = x;
        if (++putptr == items.length) putptr = 0;
        ++count;
        notEmpty.signal();
    }
}
