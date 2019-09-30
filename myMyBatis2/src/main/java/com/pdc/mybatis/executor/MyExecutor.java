package com.pdc.mybatis.executor;

import com.pdc.mybatis.config.MapperData;
import com.pdc.mybatis.config.MyConfiguration;

/**
 * 操作sql
 */
public interface MyExecutor {
    public <T> T query(MapperData mapperData, Object parameter);
}
