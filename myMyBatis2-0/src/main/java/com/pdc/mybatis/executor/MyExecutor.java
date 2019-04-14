package com.pdc.mybatis.executor;

import com.pdc.mybatis.config.MapperData;
import com.pdc.mybatis.config.MyConfiguration;

/**
 * 操作sql
 */
public interface MyExecutor {

    public static MyExecutor DEFAULT(MyConfiguration configuration) {

        return null;
    }

    public <E> E query(MapperData mapperData, Object parameter);
}
