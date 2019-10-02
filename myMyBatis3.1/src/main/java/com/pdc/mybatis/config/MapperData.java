package com.pdc.mybatis.config;

import lombok.Data;

/**
 * @author PDC
 * sql和实体类型的映射
 */
@Data
public class MapperData<T> {
    private String sql;
    private Class<T> type;

    public MapperData(String sql, Class<T> type) {
        this.sql = sql;
        this.type = type;
    }
}
