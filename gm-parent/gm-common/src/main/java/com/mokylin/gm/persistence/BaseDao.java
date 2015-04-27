package com.mokylin.gm.persistence;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

/**
 * 基础的Dao父类，所有的dao可以继承此类
 * Created by yongbo.chen
 * Time:2014/6/13 12:04
 */
public abstract class BaseDao {

    /**
     * 抽象方法,每个数据库的Dao都必须实现，对数据库进行增删改查
     * @return
     */
    protected abstract NamedParameterJdbcTemplate getJdbcNamed();

    protected abstract JdbcTemplate getJdbcTemplate();

    /**
     * 抽象方法,每个数据库的Dao都必须实现，调用数据库的函数和存储过程
     * @return
     */
    protected abstract SimpleJdbcCall getJdbcCall();
}
