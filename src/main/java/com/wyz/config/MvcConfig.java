package com.wyz.config;

import com.wyz.common.JacksonObjectMapper;
import com.wyz.utils.LoginInterceptor;
import com.wyz.utils.RefreshTokenInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import javax.annotation.Resource;
import java.util.List;

@Configuration
@Slf4j
public class MvcConfig implements WebMvcConfigurer {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    //配置拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //登录拦截器
        registry.addInterceptor(new LoginInterceptor())
                .excludePathPatterns(
                        "/user/code",
                        "/user/loginByNickName",
                        "/user/loginByPhone",
                        "/user/register"
                ).order(1);
        //token刷新的拦截器，下面的拦截器会拦截所有请求,下面拦截器的order(0)表示优先级更高
        registry.addInterceptor(new RefreshTokenInterceptor(stringRedisTemplate)).addPathPatterns("/**").order(0);
    }

    /**
     * 扩展mvc框架的消息转换器(主要用于解决id的精度问题，这个转换器可以将Long型的id转为字符串)
     * @param converters
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        log.info("扩展消息转换器");
        //创建消息转换器对象
        MappingJackson2HttpMessageConverter messageConverter=new MappingJackson2HttpMessageConverter();
        //设置对象转换器，底层使用Jackson将Java对象转为json
        messageConverter.setObjectMapper(new JacksonObjectMapper());
        //将上面的消息转换器对象追加到mvc框架的转换器容器中(下标是0，优先使用)
        converters.add(0,messageConverter);
    }

    /**
     * Docket对象就是文档
     * @return
     */
    @Bean
    public Docket createRestApi() {
        // 文档类型
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.wyz.controller"))//生成接口需要扫描controller包
                .paths(PathSelectors.any())
                .build();
    }

    //描述接口文档
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("wyz社区管理平台")
                .version("1.0")
                .description("社区管理平台接口文档")
                .build();
    }
}
