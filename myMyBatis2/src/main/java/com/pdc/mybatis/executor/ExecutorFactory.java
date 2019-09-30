package com.pdc.mybatis.executor;

import com.pdc.mybatis.config.MyConfiguration;
import com.pdc.mybatis.executor.impl.CachingExecutor;
import com.pdc.mybatis.executor.impl.SimpleExecutor;

/**
 * @author pdc
 */
public class ExecutorFactory {

    private static final String SIMPLE = "SIMPLE";
    private static final String CACHING = "CACHING";

    public static MyExecutor get(){
        return get("SIMPLE");
    }

    public static MyExecutor get(String key) {
        if (SIMPLE.equalsIgnoreCase(key)) {
            return new SimpleExecutor();
        }else if (CACHING.equalsIgnoreCase(key)) {
            return new CachingExecutor(new SimpleExecutor());
        }
        throw new RuntimeException("No this Executor");
    }
}
