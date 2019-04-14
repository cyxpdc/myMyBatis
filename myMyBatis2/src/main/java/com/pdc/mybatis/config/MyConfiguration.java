package com.pdc.mybatis.config;

import lombok.Data;

/**
 * 用来让使用者编写的mapper接口找到其配置
 * author PDC
 */
@Data
public class MyConfiguration {
    /**
     * mapper接口的位置
     */
    private String scanPath;
    /**
     * 接口方法：对应的sql
     */
    private MapperRegistory mapperRegistory = new MapperRegistory();

    public void build(){
        if(null == scanPath || scanPath.length() < 1){
            throw new RuntimeException("scan path is required");
        }
    }
}
