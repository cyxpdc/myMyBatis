package com.pdc.mybatis.config;

import com.pdc.mybatis.test.Test;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;
@Data
public abstract class MapperRegistory {
    /**
     * key为nameSpace要调用的方法，value为对应的数据
     */
    public static final Map<String,MapperData> methodSqlMapping = new HashMap<>();

    public abstract void put();

    public MapperData get(String nameSpace){
        return methodSqlMapping.get(nameSpace);
    }
}
