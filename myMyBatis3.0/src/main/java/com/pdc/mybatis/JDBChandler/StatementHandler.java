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
        }
        return null;
    }

    private Connection getConnection() {
        try {
            Properties properties = new Properties();
            File file = new File("com/pdc/mybatis/jdbc.properties");
            InputStream inputStream = new FileInputStream(file);
            properties.load(inputStream);

            String URL = properties.getProperty("URL");;
            String USER = properties.getProperty("USER");;
            String PASSWORD=properties.getProperty("PASSWORD");;
            Class.forName(properties.getProperty("CLASS"));
            inputStream.close();
            return DriverManager.getConnection(URL,USER,PASSWORD);
        } catch (Exception e) {
            return null;
        }
    }
}
