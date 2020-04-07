package com.ausware.thinking.in.spring.ioc.overview.dependency.lookup;

import com.ausware.thinking.in.spring.ioc.overview.annotation.Super;
import com.ausware.thinking.in.spring.ioc.overview.entity.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

/**
 * 依赖查找
 * @author tuk
 * @create 2020-04-07 12:39
 **/
public class DependencyLookupDemo {

    public static void main(String[] args) {
        //配置xml配置文件
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:/META-INF/dependency-lookup-context.xml");

        //根据名称，实时查找
        lookupInRealTime(beanFactory);
        //按类型，实时查找
        lookupByType(beanFactory);
        //借助ObjectFactory，延时查找
        lookupInLazy(beanFactory);
        //根据指定类型，实时查找
        lookupCollectionByType(beanFactory);
        //根据注解类型，实时查找
        lookupByAnnotationType(beanFactory);
    }

    /**
     * 方式一：根据名称实时查找
     * @param beanFactory
     */
    private static void lookupInRealTime(BeanFactory beanFactory) {
        User user = (User) beanFactory.getBean("user");
        System.out.println("根据名称，实时查找：" + user);
    }

    /**
     * 方式二：根据类型实时查找
     * @param beanFactory
     */
    private static void lookupByType(BeanFactory beanFactory) {
        User user = beanFactory.getBean(User.class);
        System.out.println("根据类型，实时查找：" + user);
    }

    /**
     * 方式三：延时查找，借助 ObjectFactory
     * @param beanFactory
     */
    private static void lookupInLazy(BeanFactory beanFactory) {
        ObjectFactory<User> objectFactory = (ObjectFactory<User>) beanFactory.getBean("objectFactory");
        User user = objectFactory.getObject();
        System.out.println("借助ObjectFactory，实现延时查找：" + user);
    }

    /**
     * 方法四：根据指定类型实时查找
     * @param beanFactory
     */
    private static void lookupCollectionByType(BeanFactory beanFactory) {
        if (beanFactory instanceof ListableBeanFactory) {
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
            Map<String, User> users = listableBeanFactory.getBeansOfType(User.class);
            System.out.println("查找到的所有的 User 集合对象：" + users);
        }
    }

    /**
     * 方法五：根据注解类型实时查找
     * @param beanFactory
     */
    public static void lookupByAnnotationType(BeanFactory beanFactory) {
        if(beanFactory instanceof ListableBeanFactory) {
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
            Map<String, User> users = (Map) listableBeanFactory.getBeansWithAnnotation(Super.class);
            System.out.println("查找所有类型的集合对象：" + users);
        }
    }


}
