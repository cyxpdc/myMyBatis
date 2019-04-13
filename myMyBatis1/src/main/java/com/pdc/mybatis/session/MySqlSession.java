package com.pdc.mybatis.session;

import com.pdc.mybatis.config.MyConfiguration;
import com.pdc.mybatis.executor.MyExecutor;
import com.pdc.mybatis.executor.impl.SimpleExecutor;
import com.pdc.mybatis.mapper.MapperProxy;
import lombok.Data;

import java.lang.reflect.Proxy;

/**
 * sql会话，用来对接配置configuration和操作sql的executor
 */
@Data
public class MySqlSession {
    private MyConfiguration configuration;
    private MyExecutor executor = new SimpleExecutor();

    public <T> T selectOne(String statement,Object parameter){
        return executor.query(statement,parameter);
    }

    public <T> T getMapper(Class<T> clazz){
        return (T) Proxy.newProxyInstance(
                clazz.getClassLoader(),//类加载器
                new Class[]{clazz},//接口
                new MapperProxy(this,clazz));//代理类
    }
}
