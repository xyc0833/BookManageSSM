package com.study.initialize;

import com.study.config.MainConfiguration;
import com.study.config.MvcConfiguration;
import com.study.config.SecurityConfiguration;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class MvcInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{MvcConfiguration.class, SecurityConfiguration.class, MainConfiguration.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[0]; //配置DispatcherServlet的配置类、主要用于Controller等配置，
        // 这里为了教学简单，就不分这么详细了，只使用上面的基本配置类
    }

    @Override
    protected String[] getServletMappings() {
        // 表示 匹配当前 Web 应用下的「所有请求路径」
        return new String[]{"/"};//匹配路径，与上面一致
    }
}
