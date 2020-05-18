package com.ausware.thinking.in.spring.bean.definition;

import com.ausware.thinking.in.spring.ioc.overview.entity.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * bean实例化demo
 *
 * @author
 * @create 2020-04-09 8:31
 **/
public class BeanInstantiationDemo {

    public static void main(String[] args) {
        // 配置 XML 配置文件
        // 启动 Spring 应用上下文
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:/META-INF/bean-instantiation-context.xml");
        //获取静态方法实例化bean
        User userOfStatic = beanFactory.getBean("user-by-static-method", User.class);
        //获取FactoryBean实例化的bean
        User userOfFactoryBean = beanFactory.getBean("user-by-factory-bean", User.class);
        //获取实例化的bean
        User userOfInstantation = beanFactory.getBean("user-by-instance-method", User.class);
        System.out.println(userOfStatic);
        System.out.println(userOfFactoryBean);
        System.out.println(userOfInstantation);

        System.out.println(userOfStatic == userOfFactoryBean);
        System.out.println(userOfStatic == userOfInstantation);
    }

}
