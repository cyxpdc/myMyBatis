package com.pdc.mybatis.session;

import com.pdc.mybatis.config.MapperData;
import com.pdc.mybatis.config.MyConfiguration;
import com.pdc.mybatis.executor.MyExecutor;
import com.pdc.mybatis.proxy.MapperProxy;
import lombok.Data;

import java.lang.reflect.Proxy;

/**
 * @author pdc
 * sql会话，用来对接配置configuration和操作sql的executor
 */

public class MySqlSession {

    private MyConfiguration configuration;
    private MyExecutor executor;

    public MySqlSession(MyConfiguration configuration, MyExecutor executor) {
        this.configuration = configuration;
        this.executor = executor;
    }

    //mybatis的动态代理不需要实现类，由xml来代替，让接口和sql一一对应即可
    //不用实现类，方便使用者，xml也可以开发者帮使用者实现，只要命名规范即可
    public <T> T getMapper(Class<T> clazz){
        return (T) Proxy.newProxyInstance(
                clazz.getClassLoader(),//类加载器
                new Class[]{clazz},//接口
                new MapperProxy(this));//代理类
    }

    public <T> T selectByPrimaryKey(MapperData mapperData, Object parameter){
        return executor.query(mapperData,parameter);
    }

    public MyConfiguration getConfiguration() {
        return configuration;
    }
}
