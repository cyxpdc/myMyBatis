package com.pdc.mybatis.test;

import com.pdc.mybatis.config.MapperData;
import com.pdc.mybatis.config.MapperRegistory;

/**
 * @author PDC
 */
public class SimpleMapperRegistory extends MapperRegistory {
    /**
     * Java Bean的属性顺序要和数据库表的名字一致
     */
    @Override
    public void put(){
        //在这里配置，起xml文件的作用
        //在configuration中调用put
        methodSqlMapping.put("TestMapper.selectByPrimaryKey",
                new MapperData("select * from test where id=%d", Test.class));
    }
}
