package com.pdc.mybatis.config;

import com.pdc.mybatis.test.Test;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;
@Data
public class MapperRegistory {
    /**
     * key为nameSpace要调用的方法，value为对应的数据
     */
    public static final Map<String,MapperData> methodSqlMapping = new HashMap<>();

    /**
     * Java Bean的属性顺序要和数据库表的名字一致
     */
    public MapperRegistory() {
        put();
    }

    public void put(){
        methodSqlMapping.put("com.pdc.mybatis.test.TestMapper.selectByPrimaryKey",
                new MapperData("select * from test where id = %d", Test.class));
    }

    public MapperData get(String nameSpace){
        return methodSqlMapping.get(nameSpace);
    }
}
