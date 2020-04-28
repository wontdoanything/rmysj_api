package org.rmysj.api.adapter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import org.rmysj.api.interceptor.SecurityInterceptor;


@Configuration
public class WebAppConfig extends WebMvcConfigurerAdapter {
	public static void main(String[] args) {
        SpringApplication.run(WebAppConfig.class, args);
    } 
     

    
    @Bean
    public SecurityInterceptor securityInterceptor() {
        return new SecurityInterceptor();
    }
    
    /**
     * 配置拦截器
     * @author
     * @param registry
     */
    public @Override void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(securityInterceptor()).addPathPatterns("/api/**");
        registry.addInterceptor(securityInterceptor()).addPathPatterns("/aec/**");
    }

    @Bean
    @ConfigurationProperties(prefix = "rest.connection")
    public HttpComponentsClientHttpRequestFactory httpRequestFactory() {
        return new HttpComponentsClientHttpRequestFactory();
    }

    @Bean
    public RestTemplate customRestTemplate(){
        return new RestTemplate(httpRequestFactory());
    }

}

