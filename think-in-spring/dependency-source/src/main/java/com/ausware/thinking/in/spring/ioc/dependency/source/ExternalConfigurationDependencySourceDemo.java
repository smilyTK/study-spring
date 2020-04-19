package com.ausware.thinking.in.spring.ioc.dependency.source;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;

/**
 * 外部化配置作为依赖来源demo
 *
 * @author
 * @create 2020-04-19 10:53
 **/
@Configuration
@PropertySource(value = "META-INF/default.properties", encoding = "utf-8")
public class ExternalConfigurationDependencySourceDemo {

    @Value(value = "${usr.id}")
    private Long id;
    @Value(value = "${usr.name}")
    private String name;
    @Value(value = "${usr.resource}")
    private Resource resource;

    public static void main(String[] args) {
        //创建 BeanFactory 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        //注册 Configuration class
        applicationContext.register(ExternalConfigurationDependencySourceDemo.class);

        //启动 spring 应用上下文
        applicationContext.refresh();

        //
        ExternalConfigurationDependencySourceDemo demo = applicationContext.getBean(ExternalConfigurationDependencySourceDemo.class);

        //依赖查找
        System.out.println("demo.id = " + demo.id);
        System.out.println("demo.name = " + demo.name);
        System.out.println("demo.resource = " + demo.resource);

        applicationContext.close();

    }

}
