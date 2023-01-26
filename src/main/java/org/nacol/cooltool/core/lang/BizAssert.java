package org.nacol.cooltool.core.lang;

import cn.hutool.core.util.ReflectUtil;
import com.alibaba.fastjson2.JSONObject;
import org.nacol.cooltool.exception.BizException;

import java.util.function.Function;

/**
 * @Author Nacol(姚秋实)
 * @Date 2023/1/26
 * @Description 业务断言
 */
public class BizAssert {

    public static <T> void isNull(T entity) {
        if (NullUtil.isNull(entity)) {
            throw new BizException("请求参数为空。");
        }
    }

    public static <T> void isNull(T entity, String message) {
        if (NullUtil.isNull(entity)) {
            throw new BizException(message);
        }
    }

    public static <T, R> void isNull(T entity, String message, Function<T, R> field) {
        isNull(entity);
        Object val = field.apply(entity);
        if (NullUtil.isNull(entity)) {
            throw new BizException(message);
        }
    }

    public static <T, R> void isNull(T entity, Function<T, R> ... fields) {
        isNull(entity);
        for (Function<T, R> field : fields) {
            Object val = field.apply(entity);
            if (NullUtil.isNull(entity)) {
                // TODO 可获取属性上注解上的值。
                throw new BizException("请求参数为空。");
            }
        }
    }

    public static <T, R> void isNull(T entity, Function<T, R> field) {
        isNull(entity, "请求参数为空.", field);
    }

    public static <T> void idIsNull(T entity) {
        if (NullUtil.isNull(entity)) {
            throw new BizException("ID为空。");
        }
        Object id;
        // STEP 字符串类型
        if (entity instanceof String) {
            id = entity;
        }
        // STEP JSON
        else if (entity instanceof JSONObject) {
            String jsonStr = JSONObject.toJSONString(entity);
            JSONObject jsonObject = JSONObject.parse(jsonStr);
            id = jsonObject.get("id");
        }
        // STEP 对象
        else {
            id = ReflectUtil.getFieldValue(entity, "id");
        }

        // STEP 判断
        if (NullUtil.isNull(entity))
            throw new BizException("ID为空。");
    }

    public static void main(String[] args) {
//        idIsNull("param");
//
//        JSONObject param = new JSONObject();
//        param.put("id", 123);
//        idIsNull(param);
//
//        Student object = new Student()
//                .setId("")
//                ;
//        idIsNull(object);
    }
}
