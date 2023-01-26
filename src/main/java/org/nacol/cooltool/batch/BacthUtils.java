package org.nacol.cooltool.batch;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.StopWatch;
import cn.hutool.core.util.ReflectUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author Nacol(姚秋实)
 * @Date 2022/11/9
 * @Description 批量插入工具
 */
@Log4j2
public class BacthUtils {

    @Autowired
    private static DataSource defaultDataSourece;

    public static final int CORE_NUM = Runtime.getRuntime().availableProcessors();

    public static <T> void batchInsert(List<T> list, String sql, int batchNum) {
        batchInsert(list, sql, batchNum, 2, "", defaultDataSourece);
    }

    public static <T> void batchInsert(List<T> list, String sql, int batchNum, String processName) {
        batchInsert(list, sql, batchNum, 2, processName, defaultDataSourece);
    }

    public static <T> void batchInsert(List<T> list, String sql, int batchNum, String processName, DataSource dataSource) {
        batchInsert(list, sql, batchNum, 2, processName, dataSource);
    }

    public static <T> void batchInsert(List<T> list, String sql, int batchNum, double coreRatio, String processName, DataSource dataSource) {
        StopWatch stopWatch = new StopWatch(processName);
        stopWatch.start("批量新增");
        // STEP 每个数组长度
        int listSize = list.size() / (int) (CORE_NUM * coreRatio);
        // STEP 切分数组
        List<List<T>> split = CollectionUtil.split(list, listSize);
        split.parallelStream().forEach(objs -> insertBatchData(objs, sql, batchNum, dataSource));
        stopWatch.stop();
        log.info(stopWatch.prettyPrint(TimeUnit.MILLISECONDS));
    }

    /**
     * @Author Nacol(姚秋实)
     * @Date 2022/11/9
     * @Description 插入数据
     */
    private static <T> void insertBatchData(List<T> objs, String sql, int batchNum, DataSource dataSource) {
        StopWatch sw = new StopWatch();
        sw.start("批量新增-分组");
        try (Connection conn = dataSource.getConnection();
             PreparedStatement insertStatement = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);
            for (int i = 0; i < objs.size(); i++) {
                T t = objs.get(i);
                initStatement(t, insertStatement);
                insertStatement.addBatch();
                if (i % batchNum == 0) {
                    insertStatement.executeBatch();
                    conn.commit();
                    insertStatement.clearBatch();
                }
            }
            insertStatement.executeBatch();
            conn.commit();
            insertStatement.clearBatch();
            sw.stop();
            log.info(sw.prettyPrint(TimeUnit.MILLISECONDS));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Author Nacol(姚秋实)
     * @Date 2022/11/9
     * @Description 初始化 statement
     */
    private static <T> void initStatement(T t, PreparedStatement insertStatement) throws IllegalAccessException, SQLException {
        Field[] fields = ReflectUtil.getFields(t.getClass());
        int index = 1;
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            // 不处理 final 类型字段
            if (Modifier.isFinal(field.getModifiers())) {
                continue;
            }
            field.setAccessible(true);
            Object val = field.get(t);
            if (val == null) {
                continue;
            }
            if (field.getType().isAssignableFrom(byte.class) || field.getType().isAssignableFrom(Byte.class)) {
                insertStatement.setByte(index, (byte)val);
            } else if (field.getType().isAssignableFrom(short.class) || field.getType().isAssignableFrom(Short.class)) {
                insertStatement.setShort(index, (short)val);
            } else if (field.getType().isAssignableFrom(int.class) || field.getType().isAssignableFrom(Integer.class)) {
                insertStatement.setInt(index, (int)val);
            } else if (field.getType().isAssignableFrom(long.class) || field.getType().isAssignableFrom(Long.class)) {
                insertStatement.setLong(index, (long)val);
            } else if (field.getType().isAssignableFrom(float.class) || field.getType().isAssignableFrom(Float.class)) {
                insertStatement.setFloat(index, (float)val);
            } else if (field.getType().isAssignableFrom(double.class) || field.getType().isAssignableFrom(Double.class)) {
                insertStatement.setDouble(index, (double)val);
            } else if (field.getType().isAssignableFrom(char.class) || field.getType().isAssignableFrom(Character.class) || field.getType().isAssignableFrom(String.class)) {
                insertStatement.setString(index, (String)val);
            } else if (field.getType().isAssignableFrom(boolean.class) || field.getType().isAssignableFrom(Boolean.class)) {
                insertStatement.setBoolean(index, (boolean)val);
            } else if (field.getType().isAssignableFrom(BigDecimal.class)) {
                insertStatement.setBigDecimal(index, (BigDecimal)val);
            }
            ++index;
        }
    }

}
