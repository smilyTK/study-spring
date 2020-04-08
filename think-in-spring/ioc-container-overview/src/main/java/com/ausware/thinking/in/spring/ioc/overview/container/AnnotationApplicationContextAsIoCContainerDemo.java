package com.ausware.thinking.in.spring.ioc.overview.container;

import com.ausware.thinking.in.spring.ioc.overview.entity.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * 基于注解的Ioc容器
 *
 * @author
 * @create 2020-04-08 8:33
 **/
@Configuration
public class AnnotationApplicationContextAsIoCContainerDemo {

    public static void main(String[] args) {
        //创建 BeanFactory 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        //将当前类 AnnotationApplicationContextAsIoCContainerDemo作为配置类
        applicationContext.register(AnnotationApplicationContextAsIoCContainerDemo.class);
        //启动上下文
        applicationContext.refresh();
        //依赖查找
        lookupCollectionByType(applicationContext);
        //关闭上下文
        applicationContext.close();
    }

    /**
     * 通过java注解的方式，定义一个Bean
     * @return
     */
    @Bean
    public User user() {
        User user = new User();
        user.setId(1L);
        user.setName("tu");
        return user;
    }

    /**
     * 查找指定类型集合
     * @param beanFactory
     */
    private static void lookupCollectionByType(BeanFactory beanFactory) {
        if(beanFactory instanceof ListableBeanFactory) {
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
            Map<String, User> userMap = listableBeanFactory.getBeansOfType(User.class);
            System.out.println(userMap);
        }
    }
}
