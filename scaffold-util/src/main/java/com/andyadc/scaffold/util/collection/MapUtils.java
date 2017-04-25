package com.andyadc.scaffold.util.collection;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Provides utility methods
 *
 * @author andaicheng
 */
public class MapUtils {

    /**
     * <code>MapUtils</code> should not normally be instantiated.
     */
    private MapUtils() {
    }

    /**
     * Null-safe check if the specified map is empty.
     * <p>
     * Null returns true.
     *
     * @param map the map to check, may be null
     * @return true if empty or null
     */
    public static boolean isEmpty(final Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    /**
     * Null-safe check if the specified map is not empty.
     * <p>
     * Null returns false.
     *
     * @param map the map to check, may be null
     * @return true if non-null and non-empty
     */
    public static boolean isNotEmpty(final Map<?, ?> map) {
        return !isEmpty(map);
    }

    /**
     * <p>Reverses a Map<K, V> to Map<V, K>.</p>
     */
    public static <K, V> Map<V, K> reverse(Map<K, V> source) {
        Map<V, K> target = null;
        if (isNotEmpty(source)) {
            target = new LinkedHashMap<>(source.size());
            for (Map.Entry<K, V> entry : source.entrySet()) {
                target.put(entry.getValue(), entry.getKey());
            }
        }
        return target;
    }
}
