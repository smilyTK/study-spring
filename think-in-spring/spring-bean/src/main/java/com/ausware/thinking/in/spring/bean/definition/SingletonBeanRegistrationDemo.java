package com.ausware.thinking.in.spring.bean.definition;

import com.ausware.thinking.in.spring.bean.factory.DefaultUserFactory;
import com.ausware.thinking.in.spring.bean.factory.UserFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 单例bean注册实例
 *
 * @author
 * @create 2020-04-09 8:43
 **/
public class SingletonBeanRegistrationDemo {

    public static void main(String[] args) {
        //创建BeanFactory
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        //注册外部单例对象
        UserFactory userFactory = new DefaultUserFactory();

        ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
        //将外部的实例对象委托给BeanFactory
        beanFactory.registerSingleton("userFactory", userFactory);
        //启用应用上下文
        applicationContext.refresh();
        //依赖查找
        UserFactory userFactoryByLookup = beanFactory.getBean("userFactory", UserFactory.class);
        System.out.println("userFactory == userFactoryByLookup ：" + ( userFactory == userFactoryByLookup ));
        //关闭Spring应用上下文
        applicationContext.close();
    }

}
