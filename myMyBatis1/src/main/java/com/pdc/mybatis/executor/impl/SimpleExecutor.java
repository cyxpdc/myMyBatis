package com.pdc.mybatis.executor.impl;

import com.pdc.mybatis.executor.MyExecutor;
import com.pdc.mybatis.test.Test;

import java.sql.*;

public class SimpleExecutor implements MyExecutor{

    @Override
    public <T> T query(String sql, Object parameter) {
        //jdbc代码
        try{
            Connection conn = getConnection();
            PreparedStatement pstmt;
            pstmt = conn.prepareStatement(String.format(sql,Integer.parseInt(String.valueOf(parameter))));
            ResultSet rs = pstmt.executeQuery();
            Test test = new Test();
            while(rs.next()){
                test.setId(rs.getInt(1));
                test.setName(rs.getString(1));
            }
            return (T) test;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    private Connection getConnection() {
        String URL="jdbc:mysql://127.0.0.1:3306/mybatis?useUnicode=true&amp;characterEncoding=utf-8";
        String USER="root";
        String PASSWORD="root";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection(URL,USER,PASSWORD);
        } catch (Exception e) {
            return null;
        }
    }
}
