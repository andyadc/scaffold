package com.andyadc.scaffold.util;

import java.util.Map;

/**
 * @author andaicheng
 */
public class MapUtil {

    private MapUtil() {
    }

    public static boolean isEmpty(final Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    public static boolean isNotEmpty(final Map<?, ?> map) {
        return !isEmpty(map);
    }
}
