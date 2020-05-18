package com.ausware.thinking.in.spring.ioc.bean.lifecycle;

import com.ausware.thinking.in.spring.ioc.overview.entity.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * userHolder
 *
 * @author
 * @create 2020-05-06 8:33
 **/
public class UserHolder implements BeanNameAware, BeanClassLoaderAware, BeanFactoryAware, EnvironmentAware,
        InitializingBean, SmartInitializingSingleton,
        DisposableBean{

    private final User user;
    private Integer number;
    private String description;


    private ClassLoader classLoader;
    private BeanFactory beanFactory;
    private String beanName;
    private Environment environment;

    public UserHolder(User user) {
        this.user = user;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "UserHolder{" +
                "user=" + user +
                ", number=" + number +
                ", description='" + description + '\'' +
                ", beanName='" + beanName + '\'' +
                '}';
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setBeanName(String name) {
        this.beanName = beanName;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }


    //Spring bean 初始化阶段

    /**
     * 初始化阶段：1. 基于@PostConstruct注解回调，实现方法回调
     * @throws Exception
     */
    @PostConstruct
    public void initPostConstruct() {
        this.description = "this user holder V4";
        System.out.println("initPostConstruct() = " + description);
    }
    /**
     * 初始化阶段：2. 基于InitializingBean接口回调，实现方法回调
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        //Bean初始化前
        this.description = "this user holder V5";
        System.out.println("afterPropertiesSet() = " + description);
    }
    /**
     * 初始化阶段：3. 自定义初始化方法
     */
    public void init() {
        this.description = "this user holder V6";
        System.out.println("init() = " + description);
    }
    /**
     * 初始化完成：
     */
    @Override
    public void afterSingletonsInstantiated() {
        //postProcessAfterInitialization -> afterSingletonsInstantiated
        this.description = "this user holder V8";
        System.out.println("afterSingletonsInstantiated() = " + description);
    }
    /**
     * 销毁阶段：1. @PreDestroy
     */
    @PreDestroy
    public void preDestory() {
        // postProcessBeforeDestruction : The user holder V9
        this.description = "The user holder V10";
        System.out.println("preDestroy() = " + description);
    }
    /**
     * 销毁阶段：2. disposableBean.destroy()
     */
    @Override
    public void destroy() throws Exception {
        // preDestroy : The user holder V10
        this.description = "The user holder V11";
        System.out.println("destroy() = " + description);
    }
    public void doDestroy() throws Exception {
        // destroy : The user holder V11
        this.description = "The user holder V12";
        System.out.println("doDestroy() = " + description);
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("The UserHolder is finalized...");
    }
}
