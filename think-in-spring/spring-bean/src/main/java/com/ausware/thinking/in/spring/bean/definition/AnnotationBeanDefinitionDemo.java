package com.ausware.thinking.in.spring.bean.definition;

import com.ausware.thinking.in.spring.ioc.overview.entity.User;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 使用注解构建beanDefinition
 *
 * @author
 * @create 2020-04-09 8:45
 **/
// 3. 通过 @Import 来进行导入
@Import(AnnotationBeanDefinitionDemo.Config.class)
public class AnnotationBeanDefinitionDemo {

    public static void main(String[] args) {
        //创建应用上下文
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        //注册 Configuration class 配置类
        applicationContext.register(AnnotationBeanDefinitionDemo.class);

        //通过BeanDefinition 注册Api实现
        // 1.命名 Bean 的注册方式
        registerUserBeanDefinition(applicationContext, "user-tuk");
        // 2. 非命名 Bean 的注册方法
        registerUserBeanDefinition(applicationContext);

        //启用应用上下文
        applicationContext.refresh();
        // 按照类型依赖查找
        System.out.println("Config 类型的所有 Beans" + applicationContext.getBeansOfType(Config.class));
        System.out.println("User 类型的所有 Beans" + applicationContext.getBeansOfType(User.class));
        // 显示地关闭 Spring 应用上下文
        applicationContext.close();

    }

    /**
     * 通过BeanDefinitionBuilder建造
     * @param registry
     * @param beanName
     */
    public static void registerUserBeanDefinition(BeanDefinitionRegistry registry, String beanName) {
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(User.class);
        beanDefinitionBuilder.addPropertyValue("id", 111L).addPropertyValue("name", "bd-tu");
        if(StringUtils.hasText(beanName)) {
            //手动命名，注册BeanDefinition
            registry.registerBeanDefinition(beanName, beanDefinitionBuilder.getBeanDefinition());
        } else {
            //非手动命名
            BeanDefinitionReaderUtils.registerWithGeneratedName(beanDefinitionBuilder.getBeanDefinition(), registry);
        }
    }

    /**
     *
     * @param registry
     */
    public static void registerUserBeanDefinition(BeanDefinitionRegistry registry) {
        registerUserBeanDefinition(registry, null);
    }

    // 2. 通过 @Componet方式
    @Component  //定义当前类作为 Spring Bean组件
    public static class Config {
        // 1. 通过@Bean 方式定义

        /**
         * 通过Java 注解方式，定义一个Bean
         *
         */
        @Bean(name = {"user", "tuk-user"})
        public User user() {
            User user = new User();
            user.setId(11L);
            user.setName("tu");
            return user;
        }
    }
}
