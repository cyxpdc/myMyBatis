package com.pdc.mybatis.executor.impl;

import com.pdc.mybatis.JDBChandler.StatementHandler;
import com.pdc.mybatis.config.MapperData;
import com.pdc.mybatis.config.MyConfiguration;
import com.pdc.mybatis.executor.MyExecutor;

import java.util.HashMap;
import java.util.Map;

public class CachingExecutor implements MyExecutor {
    private MyConfiguration configuration;
    //装饰器模式实现缓存
    private SimpleExecutor delegate = new SimpleExecutor(configuration);

    private Map<String,Object> localCache = new HashMap();

    public CachingExecutor(MyConfiguration configuration) {
        this.configuration = configuration;
    }

    public <T> T query(MapperData mapperData, Object parameter)  {
        Object result = localCache.get(mapperData.getSql());
        if(null!= result){
            System.out.println("缓存命中");
            return (T)result;
        }
        result =  (T) delegate.query(mapperData,parameter);
        localCache.put(mapperData.getSql(),result);
        return (T)result;
    }
}