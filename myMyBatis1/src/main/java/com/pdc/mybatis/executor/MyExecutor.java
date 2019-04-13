package com.pdc.mybatis.executor;

import lombok.Data;

/**
 * 操作sql
 */
public interface MyExecutor {

    <T> T query(String sql, Object parameter);
}
