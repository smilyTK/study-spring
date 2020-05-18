package com.ausware.thinking.in.spring.bean.factory;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 默认的工厂实现类
 *
 * @author
 * @create 2020-04-08 8:46
 **/
public class DefaultUserFactory implements UserFactory, InitializingBean, DisposableBean {

    /**
     * 1. 基于 @PostConstruct 注解，完成实例化
     */
    @PostConstruct
    public void init() {
        System.out.println("基于 Java 注解的初始化方法，@PostConstruct");
    }

    /**
     * 2. 基于 Java Api，完成实例化
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("基于 Java Api，完成实例化 ，InitializingBean#afterPropertiesSet()");
    }

    /**
     * 3. 基于 @Bean(initMethod="")，完成自定义实例化
     */
    public void initUserFactory() {
        System.out.println("自定义初始化方法 initUserFactory() ");
    }

    @PreDestroy
    public void preDestroy() throws Exception {
        System.out.println("基于 Java 注解的销毁方法，@PreDestroy");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("基于 Java Api，完成销毁 DisposableBean#destroy() ");
    }

    public void destroyUserFactory() {
        System.out.println("自定义销毁方法 destroyUserFactory() ");
    }

    @Override
    public void finalize() throws Throwable {
        System.out.println("当前 DefaultUserFactory 对象正在被垃圾回收...");
    }
}
