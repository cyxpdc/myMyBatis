package com.pdc.mybatis.main;

import com.pdc.mybatis.config.MapperRegistory;
import com.pdc.mybatis.config.MyConfiguration;
import com.pdc.mybatis.executor.ExecutorFactory;
import com.pdc.mybatis.executor.MyExecutor;
import com.pdc.mybatis.session.MySqlSession;
import com.pdc.mybatis.test.SimpleMapperRegistory;
import com.pdc.mybatis.test.Test;
import com.pdc.mybatis.test.TestMapper;

public class BootStrap {
    public static void start(){
        //可通过继承MapperRegistory重写put的方式来书写自己想要的映射
        MyConfiguration configuration = new MyConfiguration();
        MapperRegistory mapperRegistory = new SimpleMapperRegistory();
        configuration.build(mapperRegistory);
        MySqlSession sqlSession = new MySqlSession(configuration, ExecutorFactory.get());
        TestMapper testMapper = sqlSession.getMapper(TestMapper.class);
        Test test = testMapper.selectByPrimaryKey(2);
        System.out.println(test);
    }

    public static void main(String[] args){
        start();
    }
}
