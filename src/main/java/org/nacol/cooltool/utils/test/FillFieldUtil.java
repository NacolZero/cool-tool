package org.nacol.cooltool.utils.test;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.ReflectUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @Author Nacol(姚秋实)
 * @Date 2022/11/7
 * @Description 填充属性工具
 */
public class FillFieldUtil {

    private static final List<String> ID = Arrays.asList("id", "id_");
    private static Snowflake snowflake = new Snowflake();

    /**
     * @Author Nacol(姚秋实)
     * @Date 2022/11/7
     * @Description 填充对象随机属性
     */
    public static <T> T fillRandomField(Class<T> clazz) {
        if (clazz == null) {
            return null;
        }
        Field[] fields = ReflectUtil.getFields(clazz);
        T obj = null;
        try {
            obj = clazz.newInstance();
            for (Field field : fields) {
                if (ID.contains(field.getName())) {
                    initId(field, obj);
                } else {
                    fillRandomValue(field, obj);
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return obj;
    }

    private static <T> void initId(Field field, T obj) {
        String typeName = field.getType().getSimpleName();
        field.setAccessible(true);
        try {
            if (StringUtils.equals(typeName, "String")) {
                field.set(obj, UUID.fastUUID().toString());
            } else if (StringUtils.equals(typeName, "Integer")
                    || StringUtils.equals(typeName, "int")) {
                field.set(obj, (int)snowflake.nextId());
            } else if (StringUtils.equals(typeName, "Long")
                    || StringUtils.equals(typeName, "long")){
                field.set(obj, snowflake.nextId());
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Author Nacol(姚秋实)
     * @Date 2022/11/7
     * @Description 给对象填充属性
     */
    private static <T> void fillRandomValue(Field field, T obj) throws IllegalAccessException {
        // 不处理 final 类型字段
        if (Modifier.isFinal(field.getModifiers())) {
            return;
        }
        // 可修改
        field.setAccessible(true);
        // 填充值
        switch (field.getType().getSimpleName()) {
            case "byte" :
                field.set(obj, (byte)new Random().nextInt(128));
                break;
            case "Byte" :
                field.set(obj, (byte)new Random().nextInt(128));
                break;
            case "short" :
                field.set(obj, (short)new Random().nextInt(128));
                break;
            case "Short" :
                field.set(obj, (short)new Random().nextInt(128));
                break;
            case "int":
                field.set(obj, new Random().nextInt(128));
                break;
            case "Integer":
                field.set(obj, new Random().nextInt(128));
                break;
            case "long":
                field.set(obj, (long)new Random().nextInt(128));
                break;
            case "Long":
                field.set(obj, (long)new Random().nextInt(128));
                break;
            case "float":
                field.set(obj, new Random().nextFloat());
                break;
            case "Float":
                field.set(obj, new Random().nextFloat());
                break;
            case "double":
                field.set(obj, new Random().nextDouble());
                break;
            case "Double":
                field.set(obj, new Random().nextDouble());
                break;
            case "char":
                field.set(obj, RandomStringUtils.random(1).toCharArray()[0]);
                break;
            case "Character":
                field.set(obj, RandomStringUtils.random(1).toCharArray()[0]);
                break;
            case "boolean":
                field.set(obj, new Random().nextBoolean());
                break;
            case "Boolean":
                field.set(obj, new Random().nextBoolean());
                break;
            case "BigDecimal":
                field.set(obj, new BigDecimal(new Random().nextDouble()).setScale(2, RoundingMode.HALF_UP));
                break;
            case "Date" :
                field.set(obj, new Date());
                break;
            case "LocalDateTime":
                field.set(obj, LocalDateTime.now());
                break;
            case "LocalDate":
                field.set(obj, LocalDate.now());
                break;
            case "LocalTime":
                field.set(obj, LocalTime.now());
                break;
            case "String":
                field.set(obj, RandomStringUtils.random(1));
                break;
        }
    }
}
