package com.pdc.mybatis.executor.impl;

import com.pdc.mybatis.config.MapperData;
import com.pdc.mybatis.config.MyConfiguration;
import com.pdc.mybatis.executor.MyExecutor;
import com.pdc.mybatis.JDBChandler.StatementHandler;

/**
 *  负责执行
 */
public class SimpleExecutor implements MyExecutor{

    private MyConfiguration configuration;

    public SimpleExecutor(MyConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <E> E query(MapperData mapperData, Object parameter){
        StatementHandler handler = new StatementHandler(configuration);
        return (E)handler.query(mapperData,parameter);
    }

}
