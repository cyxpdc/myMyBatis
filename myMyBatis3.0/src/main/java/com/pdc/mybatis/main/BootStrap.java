package com.pdc.mybatis.main;

import com.pdc.mybatis.config.MyConfiguration;
import com.pdc.mybatis.executor.ExecutorFactory;
import com.pdc.mybatis.executor.MyExecutor;
import com.pdc.mybatis.session.MySqlSession;
import com.pdc.mybatis.test.Test;
import com.pdc.mybatis.test.TestMapper;
import sun.applet.Main;

public class BootStrap {
    public static void start(){
        //创建配置类
        MyConfiguration configuration = new MyConfiguration();
        configuration.setScanPath("com.pdc.mybatis.test");
        configuration.build();
        //创建sqlSession
        MySqlSession sqlSession = new MySqlSession(configuration, ExecutorFactory.get());
        //得到mapper，执行sql
        TestMapper testMapper = sqlSession.getMapper(TestMapper.class);
        Test test = testMapper.selectByPrimaryKey(2);
        System.out.println(test);
    }

    public static void main(String[] args){
        start();
    }
}
