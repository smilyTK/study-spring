package com.ausware.thinking.in.spring.bean.definition;

import com.ausware.thinking.in.spring.bean.factory.DefaultUserFactory;
import com.ausware.thinking.in.spring.bean.factory.UserFactory;
import com.ausware.thinking.in.spring.ioc.overview.entity.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * Bean初始化demo
 *
 * @author
 * @create 2020-04-08 8:44
 **/
@Configuration
public class BeanInitializationDemo {

    public static void main(String[] args) {
        //创建 BeanFactory 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        //注册 Configuration class(配置类)
        applicationContext.register(BeanInitializationDemo.class);
        //启动应有上下文
        applicationContext.refresh();
        System.out.println("Spring应用上下文已启动...");
        //依赖查找
        UserFactory userFactory = applicationContext.getBean(UserFactory.class);
        System.out.println(userFactory);
        System.out.println("Spring应用上下文准备关闭...");
        //关闭应用上下文
        applicationContext.close();
        System.out.println("Spring应用上下文已关闭...");
    }

    /**
     * 初始化工厂方法
     * @return
     */
    @Bean(initMethod="initUserFactory", destroyMethod = "destroyUserFactory")
    @Lazy(value = false)
    public UserFactory userFactory() {
        return new DefaultUserFactory();
    }

}
