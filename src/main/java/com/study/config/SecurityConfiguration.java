package com.study.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity//开启WebSecurity相关功能
public class SecurityConfiguration {

    //这里将BCryptPasswordEncoder直接注册为Bean，Security会自动进行选择
    @Bean
    public PasswordEncoder passwordEncoder(){
        //System.out.println(new BCryptPasswordEncoder().encode("password"));
        return new BCryptPasswordEncoder();
    }

    //将“记住我”功能中的信息放到数据库里面
    @Bean //该方法会自动建表
    public PersistentTokenRepository tokenRepository(DataSource dataSource){
        JdbcTokenRepositoryImpl repository = new JdbcTokenRepositoryImpl();
        //在启动时自动在数据库中创建存储记住我信息的表，仅第一次需要，后续不需要
        //repository.setCreateTableOnStartup(true);
        repository.setDataSource(dataSource);
        return repository;
    }

    //其他配置：安全过滤规则链
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
        PersistentTokenRepository repository)throws Exception{
        //如果你学习过SpringSecurity 5.X版本，可能会发现新版本的配置方式完全不一样
        //新版本全部采用lambda形式进行配置，无法再使用之前的and()方法进行连接了
        return http
                //以下是验证请求拦截和放行配置
                //配置页面的拦截规则
                .authorizeHttpRequests(auth->{
                    //针对前端的静态资源 需要放行
                    //将所有的静态资源放行，一定要添加在全部请求拦截之前
                    auth.requestMatchers("/static/**").permitAll();
                    //让所有请求必须验证之后才能放行
                    auth.anyRequest().authenticated();
                })
                //以下是表单登录相关配置
                .formLogin(conf->{
                    conf.loginPage("/login");//将登录页设置为我们自己的登录页面
                    conf.loginProcessingUrl("/doLogin");//登录表单提交的地址，可以自定义
                    conf.defaultSuccessUrl("/");//登录成功后跳转的页面
                    conf.permitAll(); //需要把上面配置的路径放行
                })
                //关闭csrf相关配置
                .csrf(conf->{
                    conf.disable();
                })
                //"记住我" 功能
                .rememberMe(conf->{
                    //conf.alwaysRemember(false);//这里不要开启始终记住，我们需要配置为用户自行勾选
                    conf.rememberMeParameter("remember-me");//记住我表单字段，默认就是这个，可以不配置
                    conf.tokenValiditySeconds(3600 * 24 * 7);//设置 记住我的 时间 7天时间
                    conf.tokenRepository(repository);//把生成的认证令牌存到repository里(数据库)，而不是默认的内存中
                })
                .build();
    }
}
