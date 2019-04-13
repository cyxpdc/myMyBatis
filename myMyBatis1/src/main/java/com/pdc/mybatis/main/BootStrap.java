package com.pdc.mybatis.main;

import com.pdc.mybatis.session.MySqlSession;
import com.pdc.mybatis.test.Test;
import com.pdc.mybatis.test.TestMapper;
import sun.applet.Main;

public class BootStrap {
    public static void start(){
        MySqlSession sqlSession = new MySqlSession();
        TestMapper testMapper = sqlSession.getMapper(TestMapper.class);
        Test test = testMapper.selectByPrimaryKey(1);
        System.out.println(test);
    }

    public static void main(String[] args){
        start();
    }
}
