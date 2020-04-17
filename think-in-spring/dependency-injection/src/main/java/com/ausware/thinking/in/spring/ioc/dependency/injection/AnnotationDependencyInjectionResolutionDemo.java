package com.ausware.thinking.in.spring.ioc.dependency.injection;

import com.ausware.thinking.in.spring.ioc.dependency.injection.annotation.InjectionUser;
import com.ausware.thinking.in.spring.ioc.dependency.injection.annotation.MyAutowired;
import com.ausware.thinking.in.spring.ioc.overview.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import javax.inject.Inject;
import java.lang.annotation.Annotation;
import java.util.*;

import static org.springframework.context.annotation.AnnotationConfigUtils.AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME;

/**
 * 注解驱动的依赖注入处理过程
 *
 * @author
 * @create 2020-04-16 8:57
 **/
@Configuration
public class AnnotationDependencyInjectionResolutionDemo {

    @Autowired  //依赖查找
    private User user;

    @Autowired  //集合类型依赖查找
    private Map<String, User> users; // user super

    @MyAutowired
    private Optional<User> userOptional;  // superUser

    @Inject  //JSR
    private User injectionUser;

    @InjectionUser
    private User myInjectionUser;


    //基于 AutowiredAnnotationBeanPostProcessor 完成自定义注解解析，但无法兼容旧注解
//    @Bean(name=AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME)
//    public static AutowiredAnnotationBeanPostProcessor beanPostProcessor() {
//        AutowiredAnnotationBeanPostProcessor beanPostProcessor = new AutowiredAnnotationBeanPostProcessor();
//        // @Autowired + @injection + 新注解@InjectionUser
//        Set<Class<? extends Annotation>> autowiredAnnotationTypes = new LinkedHashSet<>(Arrays.asList(Autowired.class, Inject.class, InjectionUser.class));
//        beanPostProcessor.setAutowiredAnnotationTypes(autowiredAnnotationTypes);
//        return beanPostProcessor;
//    }

    @Bean
    @Order(Ordered.LOWEST_PRECEDENCE - 3)
    public static AutowiredAnnotationBeanPostProcessor beanPostProcessor() {
        AutowiredAnnotationBeanPostProcessor beanPostProcessor = new AutowiredAnnotationBeanPostProcessor();
        beanPostProcessor.setAutowiredAnnotationType(InjectionUser.class);
        return beanPostProcessor;
    }


    public static void main(String[] args) {
        // 创建 BeanFactory 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 注册 Configuration Class（配置类）
        applicationContext.register(AnnotationDependencyInjectionResolutionDemo.class);

        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(applicationContext);

        String xmlResourcePath = "classpath:/META-INF/dependency-lookup-context.xml";
        // 加载 XML 资源，解析并且生成 BeanDefinition
        beanDefinitionReader.loadBeanDefinitions(xmlResourcePath);

        // 启动 Spring 应用上下文
        applicationContext.refresh();

        // 依赖查找并且创建 Bean
        AnnotationDependencyInjectionResolutionDemo demo = applicationContext.getBean(AnnotationDependencyInjectionResolutionDemo.class);

        // user superUser
        System.out.println("demo.user = " + demo.user);
        System.out.println("demo.injectionUsser = " + demo.injectionUser);

        //
        System.out.println("demo.users = " + demo.users);
        System.out.println("demo.userOptional = " + demo.userOptional);

        System.out.println("demo.myInjectionUser = " + demo.myInjectionUser);

        // 显示地关闭 Spring 应用上下文
        applicationContext.close();
    }

}
