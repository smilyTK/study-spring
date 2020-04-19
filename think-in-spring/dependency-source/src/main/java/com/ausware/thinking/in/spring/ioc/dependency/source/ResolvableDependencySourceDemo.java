package com.ausware.thinking.in.spring.ioc.dependency.source;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * 使用游离状态对象进行依赖注入
 * 注意：游离状态，简称可解释的依赖注入，但该对象在iov容器中，仅能通过类型进行依赖注入
 *
 * @author
 * @create 2020-04-19 11:23
 **/
@Configuration
public class ResolvableDependencySourceDemo {

    @Autowired
    private String value;

    @PostConstruct
    public void init() {
        System.out.println("value = " + value);
    }

    public static void main(String[] args) {

        //创建 BeanFactory 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        //注册 Configuration class
        applicationContext.register(ResolvableDependencySourceDemo.class);

        //注册外部游离Bean
        //非spring容器管理对象，作为依赖注入的依赖源
        // ConfigurableListableBeanFactory#registerResolvableDependency
        applicationContext.addBeanFactoryPostProcessor(beanFactoryPostProcessor -> {
            beanFactoryPostProcessor.registerResolvableDependency(String.class, "hello word");
        });

        //启动 Spring 应用上下文
        applicationContext.refresh();

        //关闭 Spring 应用上下文
        applicationContext.close();

    }

}
