package com.qianfeng.bigdata.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class DataSourceConfig {

    private Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);


    private Properties build(Environment env, String prefix) {

        Properties prop = new Properties();
        prop.put("driverClassName", env.getProperty(prefix + "driver-class-name"));
        prop.put("jdbcUrl", env.getProperty(prefix + "jdbc-url"));
        prop.put("username", env.getProperty(prefix + "username"));
        prop.put("password", env.getProperty(prefix + "password"));



        prop.put("maximumPoolSize", env.getProperty(prefix + "maximum-pool-size"));
        prop.put("poolName", env.getProperty(prefix + "pool-name"));

        prop.put("connectionTimeout", env.getProperty(prefix + "connection-timeout"));
        prop.put("validationTimeout", env.getProperty(prefix + "validation-timeout"));

        prop.put("maxLifetime", env.getProperty(prefix + "max-lifetime"));
        prop.put("idleTimeout", env.getProperty(prefix + "idle-timeout"));


        prop.put("connectionTestQuery", env.getProperty(prefix + "connection-test-query"));
        prop.put("minimumIdle", env.getProperty(prefix + "minimum-idle"));
        prop.put("initializationFailTimeout", env.getProperty(prefix + "initialization-fail-timeout"));
        return prop;
    }

    @Autowired
    @Bean(name = "dataSource")
    public DataSource dataSource(Environment env) {
        Properties properties = build(env, "spring.datasource.hikari.");
        HikariConfig hikariConfig = new HikariConfig(properties);
        HikariDataSource dataSource = new HikariDataSource(hikariConfig);
        return dataSource;
    }


    @Bean(name = "jdbcTemplate")
    public JdbcTemplate primaryJdbcTemplate( @Qualifier("dataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }



}
