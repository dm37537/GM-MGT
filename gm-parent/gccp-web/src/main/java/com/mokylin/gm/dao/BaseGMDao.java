package com.mokylin.gm.dao;

import com.mokylin.gm.persistence.BaseDao;
import com.mokylin.gm.utils.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

/**
 * Created by dell .
 * Time:2014/6/13 12:19
 */
public class BaseGMDao extends BaseDao {

    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private JdbcTemplate jdbcTemplate;

    private NamedParameterJdbcTemplate jdbcNamed;

    private SimpleJdbcCall jdbcCall;

    @Override
    protected JdbcTemplate getJdbcTemplate() {
        if(null == jdbcTemplate)
            jdbcTemplate = SpringContextHolder.getBean(JdbcTemplate.class);
        SpringContextHolder.getBean(JdbcTemplate.class);
        return jdbcTemplate;
    }

    @Override
    protected NamedParameterJdbcTemplate getJdbcNamed() {
        if(null == jdbcNamed)
            jdbcNamed = SpringContextHolder.getBean(NamedParameterJdbcTemplate.class);
        return jdbcNamed;
    }

    @Override
    protected SimpleJdbcCall getJdbcCall() {
        if(null == jdbcCall)
            jdbcCall = SpringContextHolder.getBean(SimpleJdbcCall.class);
        return jdbcCall;
    }
}
