package org.nacol.cooltool.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.*;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NacolPage<T> {

    public static final int MAX_SIZE = 200;

    private T entity;

    private int number;

    private int size;

    private Map<String, String> matchMap = new HashMap<>();

    private Map<String, String> orderMap = new HashMap<>();

    public Pageable generatePageable(){
        //oder
        List<Sort.Order> orders = new ArrayList<>();
        orderMap.forEach((field, direction)->{
            Sort.Order order = new Sort.Order(Sort.Direction.fromString(direction), field);
            orders.add(order);
        });
        //sort
        Sort sort = Sort.by(orders);
        //pageable
        size = Math.min(size, MAX_SIZE);
        Pageable pageable = null;
        if (orders.size() > 0) {
            pageable = PageRequest.of(number, size, sort);
        } else {
            pageable = PageRequest.of(number, size);
        }
        return pageable;
    }

    public Example generateExample() throws IllegalAccessException{
        return Example.of(entity, generateExampleMatcher());
    }

    private ExampleMatcher generateExampleMatcher() throws IllegalAccessException{
        Class clazz = entity.getClass();
        List<Field> fields = Arrays.asList(clazz.getDeclaredFields());
        ExampleMatcher exampleMatcher = ExampleMatcher.matching();
        for (Field field : fields) {
            //filter static field
            if (Modifier.isStatic(field.getModifiers())){
                continue;
            }
            String fieldName = field.getName();
            //filter empty field
            field.setAccessible(true);
            Object val = field.get(entity);
            if (val == null) {
                continue;
            }
            String matchType = matchMap.get(fieldName);
            exampleMatcher = exampleMatcher.withMatcher(fieldName, MatchFieldEnum.getMatcherConfigurer(matchType));
        }
        return exampleMatcher;
    }

}
