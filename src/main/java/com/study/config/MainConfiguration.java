package com.study.config;

import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration //这个类用来配置jdbc
@ComponentScans({
        //@ComponentScan("com.study.controller"),
        @ComponentScan("com.study.service")
})
@MapperScan("com.study.mapper")
public class MainConfiguration {

    //mybatis-spring依赖中，为我们提供了SqlSessionTemplate类，
    // 它其实就是官方封装的一个工具类，我们可以将其注册为Bean

    //数据库相关配置
    @Bean
    public DataSource dataSource(){
        //数据源配置
        return new PooledDataSource("com.mysql.cj.jdbc.Driver",
                "jdbc:mysql://localhost:3306/study", "root", "xuyong612");
    }

    //Spring 容器中配置 MyBatis 的 SqlSessionFactory
    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource) {
        // 直接参数得到Bean对象
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        return bean;
    }

}
