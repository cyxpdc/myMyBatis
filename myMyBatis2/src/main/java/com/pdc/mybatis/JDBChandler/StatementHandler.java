package com.pdc.mybatis.handler;

import com.pdc.mybatis.config.MapperData;
import com.pdc.mybatis.config.MapperRegistory;
import com.pdc.mybatis.config.MyConfiguration;

import java.sql.*;

/*
 * 数据库操作和结果处理的分发
 * author PDC
 */
public class StatementHandler {
    private final MyConfiguration configuration;

    private final ResultSetHandler resultSetHandler;

    public StatementHandler(MyConfiguration configuration) {
        this.configuration = configuration;
        this.resultSetHandler = new ResultSetHandler(configuration);
    }

    public <E> E query(MapperData mapperData,Object parameter){
        try{
            Connection conn = getConnection();
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
