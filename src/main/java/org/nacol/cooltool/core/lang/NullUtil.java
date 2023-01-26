package org.nacol.cooltool.core.lang;

import cn.hutool.core.collection.CollectionUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Objects;

/**
 * @Author Nacol(姚秋实)
 * @Date 2023/1/26
 * @Description 判空工具
 */
public class NullUtil {

    public static boolean isNull(Object obj) {
        if (Objects.isNull(obj)) {
            return true;
        }

        // STEP 数字
        if (obj instanceof String) {
            return StringUtils.isBlank((String)obj);
        }
        // STEP 集合
        else if (obj instanceof Collection){
            return CollectionUtil.isEmpty((Collection<?>) obj);
        } else {
            return Objects.isNull(obj);
        }
    }

}
