package com.pdc.mybatis.test;

import java.util.HashMap;
import java.util.Map;

public class TestMapperXml {
    public static final String nameSpace = "com.pdc.mybatis.test.TestMapper";

    public static final Map<String,String> methodSqlMapping = new HashMap<>();

    static{
        //放入一条sql
        methodSqlMapping.put("selectByPrimaryKey","select * from test where id = %d");
    }
}
