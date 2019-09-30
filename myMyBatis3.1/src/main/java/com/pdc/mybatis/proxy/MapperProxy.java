package com.pdc.mybatis.proxy;

import com.pdc.mybatis.config.MapperData;
import com.pdc.mybatis.session.MySqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MapperProxy<T> implements InvocationHandler {

    private final MySqlSession sqlSession;

    public MapperProxy(MySqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //根据要调用的方法的全限定名从MapperRegistory的map中取出MapperData
        try {
            int seperate = method.getDeclaringClass().getName().lastIndexOf(".");
            String mapper = method.getDeclaringClass().getName().substring(seperate + 1);
            MapperData mapperData =
                    sqlSession.getConfiguration().getMapperRegistory().get(mapper + "." + method.getName());
            if(null != mapperData){
                System.out.println(String.format("SQL [%s],parameter [%s]", mapperData.getSql(), args[0]));
                //me 根据使用者调用的方法去sqlSession寻找相应的方法
                //方法名字需要与sqlSession中的方法名字一致
                //todo 可将具体方法拆出来，单独成类，继承sqlSession
                Class<?> clazz = sqlSession.getClass();
                Method realMethod = clazz.getMethod(method.getName(), new Class[]{MapperData.class, Object.class});
                return realMethod.invoke(sqlSession,mapperData,args[0]);
            }
            return method.invoke(proxy,args);
        }catch (InvocationTargetException ite) {
            throw ite.getCause();
        }
    }
}
