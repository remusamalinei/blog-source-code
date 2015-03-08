import spock.lang.Specification

/**
 * @author Remus Amalinei
 */
class ConcurrentExecutorTest extends Specification {

    void 'should execute all commands'() {
        given:
        Set<Integer> inputKeySet = new HashSet<>()
        for (int i = 0; i < 10_000; i++) {
            inputKeySet.add(i);
        }

        ConcurrentExecutor concurrentExecutor = new ConcurrentExecutor()

        when:
        Map<Integer, Object> resultStringObjectMap = concurrentExecutor.executeAllCommands(inputKeySet)

        then:
        inputKeySet.size() == resultStringObjectMap.size()
    }
}
