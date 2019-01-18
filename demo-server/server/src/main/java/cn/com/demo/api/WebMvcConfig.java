package cn.com.demo.api;

import cn.com.demo.common.MessageUtil;
import cn.com.demo.common.exception.GlobalExceptionHandler;
import cn.com.demo.common.spring.RequestAttributeArgumentResolver;
import cn.com.demo.api.interceptor.JwtInterceptor;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.validation.Validator;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author miguo
 *         Created by miguo on 2018/3/28.
 */
@Configuration
@MapperScan("cn.com.demo.api.mapper")
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private Environment env;

    @Bean
    public Validator validator() {
        return new LocalValidatorFactoryBean();
    }

    @Bean
    public MessageUtil newMessageUtil() {
        MessageUtil util = new MessageUtil();
        util.setBasenames("messages");
        return util;
    }

    @Bean
    public HttpMessageConverter<String> responseBodyConverter() {
        return new StringHttpMessageConverter(Charset.forName("UTF-8"));
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedHeaders("*")
                .allowedMethods("*")
                .allowedOrigins("*");
    }

    /**
     * swagger resources
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        //静态资源(图片，pdf等)
        registry.addResourceHandler("/media/**").addResourceLocations("file:/var/data/yueryoudao/");
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        //=======================加载自定义处理器configureMessageConverters====================
        converters.add(getMappingJackson2HttpMessageConverter());
    }

    private MappingJackson2HttpMessageConverter getMappingJackson2HttpMessageConverter() {
        //Set HTTP Message converter using a JSON implementation.
        MappingJackson2HttpMessageConverter jsonMessageConverter = new MappingJackson2HttpMessageConverter();
        // Add supported media type returned by BI API.
        jsonMessageConverter.setObjectMapper(objectMapper());
        return jsonMessageConverter;
    }

    @Bean
    ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }


    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new RequestAttributeArgumentResolver());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor())
                .addPathPatterns("/**");
    }

    @Bean
    public JwtInterceptor jwtInterceptor() {
        JwtInterceptor jwtInterceptor = new JwtInterceptor();
        jwtInterceptor.setExpire(43200000);
        List<String> paths = new ArrayList<>();
        //生产环境 加入token认证
        paths.add("/error/**=anon");
        paths.add("/media/**=anon");
        paths.add("/api/weixin/**=anon");
        if (isProdEnv()) {
            paths.add("/api/**=authc");
        }
        jwtInterceptor.setPaths(paths);
        return jwtInterceptor;
    }

    @Bean
    GlobalExceptionHandler exceptionHandler() {
        return new GlobalExceptionHandler();
    }

    /**
     * 是否为生产环境
     *
     * @return true：生产环境，false:测试环境
     */
    private boolean isProdEnv() {
        String[] profiles = env.getActiveProfiles();
        for (String profile : profiles) {
            if (profile.contains("prod")) {
                return true;
            }
        }
        return false;
    }
}
