package com.pdc.mybatis.config;

import lombok.Data;

/**
 * 用来让使用者编写的mapper接口找到其配置
 * @author PDC
 */
@Data
public class MyConfiguration {
    /**
     * 接口方法：对应的sql
     */
    private MapperRegistory mapperRegistory;

    public void build(MapperRegistory mapperRegistory){
        this.mapperRegistory = mapperRegistory;
        mapperRegistory.put();
    }
}
