package ra.concurrencycomparison;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Remus Amalinei
 */
public final class Constants {

    private Constants() {
        throw new AssertionError("constants class, not intended to be instantiated");
    }

    public static final int WORD_TO_GENERATE_COUNT = 100_000_000;

    public static final Map<Integer, String> TEN_WORD_MAP;

    public static final String POISON_PILL = "poison*pill";

    static {
        Map<Integer, String> map = new HashMap<>();
        map.put(0, "a");
        map.put(1, "b");
        map.put(2, "c");
        map.put(3, "d");
        map.put(4, "e");
        map.put(5, "f");
        map.put(6, "g");
        map.put(7, "h");
        map.put(8, "i");
        map.put(9, "j");

        TEN_WORD_MAP = Collections.unmodifiableMap(map);
    }
}
