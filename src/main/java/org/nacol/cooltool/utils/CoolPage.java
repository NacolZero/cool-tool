package org.nacol.cooltool.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public class CoolPage<T> extends Page<T> {

    T entity;

    public T getEntity() {
        return entity;
    }

}
