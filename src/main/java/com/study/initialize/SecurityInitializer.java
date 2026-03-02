package com.study.initialize;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

//配置SpringSecurity，与Mvc一样，需要一个初始化器
public class SecurityInitializer extends AbstractSecurityWebApplicationInitializer {
    //不用重写任何内容
    //这里实际上会自动注册一个Filter，SpringSecurity底层就是依靠N个过滤器实现的，我们之后再探讨
}
