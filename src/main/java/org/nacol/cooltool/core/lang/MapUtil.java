package org.nacol.cooltool.core.lang;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @Author Nacol(姚秋实)
 * @Date 2023/1/28
 * @Description Map 工具类
 */
public class MapUtil {

    public static <T, K, V> Map toMap(Collection<T> collection, Function<T, K> keyFunction, Function<T, V> valFunction) {
        Map<K, V> result = new HashMap<>();
        for (T o : collection) {
            K key = keyFunction.apply(o);
            V val = valFunction.apply(o);
            result.put(key, val);
        }
        return result;
    }

}
