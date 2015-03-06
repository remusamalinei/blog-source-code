import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.Lock

import spock.lang.Specification

/**
 * @author Remus Amalinei
 */
class BoundedBufferTest extends Specification {

    Boolean lockAcquired = false
    Boolean lockReleased = false

    Lock lockStub = Stub {
        lock() >> { lockAcquired = true }
        unlock() >> { lockReleased = true }
        newCondition() >> { Stub(Condition) }
    }

    void 'put should acquire and release the lock'() {
        given:
        BoundedBuffer boundedBuffer = new BoundedBuffer(lockStub)

        when:
        boundedBuffer.put(new Object())

        then:
        lockAcquired
        lockReleased
    }

    void 'put should execute with lock acquired'() {
        given:
        BoundedBuffer boundedBuffer = new BoundedBuffer(lockStub) {
            @Override
            void addToBuffer(Object x) throws InterruptedException {
                assert lockAcquired
            }
        }

        when:
        boundedBuffer.put(new Object())

        then:
        lockReleased
    }

    void 'put should release the lock if an exception is thrown'() {
        given:
        BoundedBuffer boundedBuffer = new BoundedBuffer(lockStub) {
            @Override
            void addToBuffer(Object x) throws InterruptedException {
                throw new RuntimeException('thrown for testing purposes')
            }
        }

        when:
        boundedBuffer.put(new Object())

        then:
        thrown(RuntimeException)
        lockReleased
    }
}
