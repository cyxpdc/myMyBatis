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
            //me 根据使用者调用的方法去sqlSession寻找相应的方法
            //方法名字需要与sqlSession中的方法名字一致
            //todo 可将具体方法拆出来，单独成类，继承sqlSession
            Class<?> clazz = sqlSession.getClass();
            Method realMethod = clazz.getMethod(method.getName(), new Class[]{MapperData.class, Object.class});
            return realMethod.invoke(sqlSession,mapperData,args);
        }
        return method.invoke(proxy,args);
    }
}
