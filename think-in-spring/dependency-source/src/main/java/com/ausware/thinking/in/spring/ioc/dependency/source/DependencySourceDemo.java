package com.ausware.thinking.in.spring.ioc.dependency.source;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

import javax.annotation.PostConstruct;

/**
 * 依赖注入和依赖查找的依赖源
 *
 * @author
 * @create 2020-04-18 12:14
 **/
@Configuration
public class DependencySourceDemo {


    //根据 AbstractApplicationContext#prepareBeanFactory() 方法，分析容器内部内置bean
    @Autowired
    private BeanFactory beanFactory;
    @Autowired
    private ResourceLoader resourceLoader;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    public void initByInjection() {
        System.out.println("beanFactory == applicationContext " + (beanFactory == applicationContext));
        System.out.println("beanFactory == applicationContext.getBeanFactory() " + (beanFactory == applicationContext.getAutowireCapableBeanFactory()));
        System.out.println("resourceLoader == applicationContext " + (resourceLoader == applicationContext));
        System.out.println("ApplicationEventPublisher == applicationContext " + (applicationEventPublisher == applicationContext));
    }

    @PostConstruct
    public void initByLookup() {
        getBean(BeanFactory.class);
        getBean(ApplicationContext.class);
        getBean(ResourceLoader.class);
        getBean(ApplicationEventPublisher.class);
    }

    private <T> T getBean(Class<T> beanType) {
        try {
            return beanFactory.getBean(beanType);
        }catch (NoSuchBeanDefinitionException e) {
            System.err.println("当前类型" + beanType.getName() + " 无法在 BeanFactory 中查找!");
        }
        return null;
    }


    public static void main(String[] args) {
        //创建 BeanFactory 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        //注册 COnfiguration class
        applicationContext.register(DependencySourceDemo.class);

        //启动应用上下文
        applicationContext.refresh();

        //依赖查找
        DependencySourceDemo demo = applicationContext.getBean(DependencySourceDemo.class);

        //关闭应用上下文
        applicationContext.close();

    }

}
