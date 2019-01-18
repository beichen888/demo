package cn.com.demo.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger2 配置
 *
 * @author miguo
 */

@Configuration
@EnableSwagger2
@ComponentScan(basePackages = {"cn.com.demo.api.controller"})
public class Swagger2Config {
    @Autowired
    private Environment env;

    @Bean
    public Docket addUserDocket() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        ApiSelectorBuilder builder = docket.apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.com.demo.api.controller"));
        // 生产环境不生成swagger
        String[] profiles = env.getActiveProfiles();
        for (String profile : profiles) {
            if(profile.contains("prod")) {
                builder.paths(PathSelectors.none()).build();
                return docket;
            }
        }
        builder.paths(PathSelectors.any()).build();
        return docket;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("育儿柚道后端API接口说明")
                .termsOfServiceUrl("http://yueryoudao.alearning.com.cn")
                .description("育儿柚道后端API接口说明")
                .license("License Version 2.0")
                .licenseUrl("http://yueryoudao.alearning.com.cn")
                .version("1.0").build();
    }
}
