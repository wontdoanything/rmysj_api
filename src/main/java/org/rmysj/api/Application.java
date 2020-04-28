package org.rmysj.api;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication
// @Configuration
// @ComponentScan
// @EnableAutoConfiguration
//@Configuration
//@ServletComponentScan
//@EnableAutoConfiguration
@EnableScheduling
@ServletComponentScan
@EnableAsync
public class Application extends SpringBootServletInitializer {


    private static String log4jPath = "/opt/soft/rmysj_api/log4j.properties";
//	private static String log4jPath = "D:" + File.separatorChar + "project" + File.separatorChar + "rmysj_api" + File.separatorChar + "log4j.properties";

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        System.out.println("------------------------");
        // 初始化log4j
        //log4jPath = Application.class.getClassLoader().getResource("").getPath() + "log4j.properties";
        PropertyConfigurator.configure(log4jPath);

        return application.sources(Application.class);
    }

    /**
     * @param maxUploadSize 通过value标签注入配置
     * @return
     */
    @Bean
    public MultipartConfigElement multipartConfigElement(@Value("${upload.maxUploadSize}") String maxUploadSize) {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        // String maxUploadSize = "3072KB";
        factory.setMaxFileSize(maxUploadSize);
        factory.setMaxRequestSize(maxUploadSize);
        return factory.createMultipartConfig();
    }

    public static void main(String[] args) throws Exception {
        System.out.println("------------------------");
        //初始化log4j
        //String log4jPath = Application.class.getClassLoader().getResource("").getPath()+"log4j.properties";
        PropertyConfigurator.configure(log4jPath);

        SpringApplication.run(Application.class, args);
    }

    /*private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        return corsConfiguration;
    }

    *//**
     * 跨域过滤器
     * @return
     *//*
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig()); // 4
        return new CorsFilter(source);
    }*/




}
