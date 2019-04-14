package com.pdc.mybatis.handler;

import com.pdc.mybatis.config.MapperData;
import com.pdc.mybatis.config.MyConfiguration;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

/**
 * author PDC
 */
public class ResultSetHandler {
    private final MyConfiguration configuration;

    public ResultSetHandler(MyConfiguration configuration) {
        this.configuration = configuration;
    }

    public <E> E handler(PreparedStatement pstmt, MapperData mapperData) throws Exception {
        //实体类对象
        Object resultObj = new DefaultObjectFactory().create(mapperData.getType());
        ResultSet rs = pstmt.getResultSet();//从数据库获取值
        //填充resultObj
        if(rs.next()){//todo 假设目前只有一行记录
            int i = 0;
            for(Field field : resultObj.getClass().getDeclaredFields()){
                setValue(resultObj,field,rs,i);
            }
        }
        return null;
    }
    //要拿到实体类，再拿到set方法，再拿到要set的属性
    private void setValue(Object resultObj,Field field,ResultSet rs,int i) throws Exception{
        //调用set方法
        Method setMethod = resultObj.getClass().getMethod("set" + upperCapital(field.getName()),field.getType());
        setMethod.invoke(resultObj,getResult(field,rs));
    }

    private Object getResult(Field field,ResultSet rs) throws SQLException{
        //todo type handlers
        Class<?> type = field.getType();
        if(Integer.class == type){
            return rs.getInt(field.getName());
        }
        if(String.class == type){
            return rs.getString(field.getName());
        }
        return rs.getString(field.getName());//默认getString
    }
    /**
     * 将首字母转换为大写，符合驼峰命名法
     */
    private String upperCapital(String name){
        String first = name.substring(0,1);
        String tail = name.substring(1);
        return first.toUpperCase() + tail;
    }
}
