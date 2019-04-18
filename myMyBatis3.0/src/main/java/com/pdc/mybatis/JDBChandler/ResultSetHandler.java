package com.pdc.mybatis.JDBChandler;

import com.pdc.mybatis.config.MapperData;
import com.pdc.mybatis.config.MyConfiguration;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 查询结果的处理
 * author PDC
 */
public class ResultSetHandler {
    /*private final MyConfiguration configuration;

    public ResultSetHandler(MyConfiguration configuration) {
        this.configuration = configuration;
    }*/

    public <E> E handler(PreparedStatement pstmt, MapperData mapperData) throws Exception {
        //创建实体类对象
        Object resultObj = new DefaultObjectFactory().create(mapperData.getType());
        ResultSet rs = pstmt.getResultSet();//从数据库获取值
        //填充resultObj
        if(rs.next()){//todo 假设目前只有一行记录
            int i = 0;
            //给所有字段设置值
            for(Field field : resultObj.getClass().getDeclaredFields()){
                setValue(resultObj,field,rs,i);
            }
        }
        return null;
    }
    //调用set方法设置值
    private void setValue(Object resultObj,Field field,ResultSet rs,int i) throws Exception{
        Method setMethod = resultObj.getClass().getMethod("set" + upperCapital(field.getName()),field.getType());
        setMethod.invoke(resultObj,getResult(field,rs));
    }
    //根据字段类型去获取ResultSet中相应的值，填充此字段
    private Object getResult(Field field,ResultSet rs) throws SQLException{
        //todo typeHandlers
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
