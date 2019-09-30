package com.pdc.mybatis.JDBChandler;

import com.pdc.mybatis.config.MapperData;
import com.pdc.mybatis.config.MyConfiguration;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/*
 * 数据库操作和结果处理的分发
 * author PDC
 */
public class StatementHandler {
    /*private final MyConfiguration configuration;

    public StatementHandler(MyConfiguration configuration) {
        this.configuration = configuration;
        this.resultSetHandler = new ResultSetHandler(configuration);
    }*/

    private final ResultSetHandler resultSetHandler = new ResultSetHandler();

    public <E> E query(MapperData mapperData,Object parameter){
        Connection conn = null;
        try{
            conn = getConnection();
            //todo ParameterHandler
            PreparedStatement pstmt;
            pstmt = conn.prepareStatement(String.format(
                        mapperData.getSql(),
                        Integer.parseInt(String.valueOf(parameter))
                    )
            );
            pstmt.execute();
            return resultSetHandler.handler(pstmt,mapperData);//结果的分发
        }  catch (Exception e) {
            System.out.println("StatementHandler#query出错");
            e.printStackTrace();
        }
        return null;
    }

    private Connection getConnection() {
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/mybatis?useUnicode=true&characterEncoding=utf-8&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        String username = "root";
        String password = "root";
        Connection conn = null;
        try {
            Class.forName(driver); //classLoader,加载对应驱动
            conn = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
