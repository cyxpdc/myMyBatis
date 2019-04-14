package com.pdc.mybatis.proxy;

import com.pdc.mybatis.config.MapperData;
import com.pdc.mybatis.session.MySqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MapperProxy<T> implements InvocationHandler {

    private final MySqlSession sqlSession;
    private final Class<T> mapperInterface;

    public MapperProxy(MySqlSession sqlSession,Class<T> clazz) {
        this.sqlSession = sqlSession;
        this.mapperInterface = clazz;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //根据要调用的方法的全限定名从MapperRegistory的map中取出MapperData
        MapperData mapperData =
                sqlSession.getConfiguration()
                        .getMapperRegistory()
                        .get(method.getDeclaringClass().getName() + "." + method.getName());
        if(null != mapperData){
            System.out.println(String.format("SQL [%s],parameter [%s]",
                                                mapperData.getSql(), args));
            return sqlSession.selectOne(mapperData,String.valueOf(args[0]));
        }
        return method.invoke(proxy,args);
    }
}
