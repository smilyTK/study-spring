package com.ausware.thinking.in.spring.ioc.bean.lifecycle;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;

/**
 * 基于AnnotatedBeanDefinitionReader进行注解解析示例
 *
 * @author
 * @create 2020-04-23 9:01
 **/
public class AnnotatedBeanDefinitionParsingDemo {

    public static void main(String[] args) {
        //创建 BeanFactory容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        //AnnotatedBeanDefinitionReader未实现 BeanDefinitionReader 接口，其实现是单独的
        AnnotatedBeanDefinitionReader beanDefinitionReader = new AnnotatedBeanDefinitionReader(beanFactory);
        //注册前
        int beanDefinttionCountBefore = beanFactory.getBeanDefinitionCount();
        //注册
        beanDefinitionReader.register(AnnotatedBeanDefinitionParsingDemo.class);
        //注册后
        int beanDefinttionCountAfter = beanFactory.getBeanDefinitionCount();
        //
        System.out.println("共加载Bean数量：" + (beanDefinttionCountAfter - beanDefinttionCountBefore));
        AnnotatedBeanDefinitionParsingDemo demo = beanFactory.getBean(AnnotatedBeanDefinitionParsingDemo.class);
        System.out.println("获取bean : " + demo);
    }

}
