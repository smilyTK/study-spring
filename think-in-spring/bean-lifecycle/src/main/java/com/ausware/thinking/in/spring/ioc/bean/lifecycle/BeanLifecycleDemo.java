package com.ausware.thinking.in.spring.ioc.bean.lifecycle;

import com.ausware.thinking.in.spring.ioc.overview.entity.User;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;

/**
 * Spring Bean生命周期demo
 *
 * @author
 * @create 2020-05-12 6:37
 **/
public class BeanLifecycleDemo {
    public static void main(String[] args) {
        //
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        //添加bean实例化后置处理器，bean进行调整，但不进行替换
        beanFactory.addBeanPostProcessor(new MyInstantiationAwarePostProcessor());
        //添加bean销毁后置处理器，bean进行调整，但不进行替换
        beanFactory.addBeanPostProcessor(new MyDestructionAwarePostProcessor());
        //添加回调，解决@postConstruct注解驱动的问题
        beanFactory.addBeanPostProcessor(new CommonAnnotationBeanPostProcessor());
        //properties路径
        String[] paths = {"/META-INF/dependency-lookup-context.xml", "/META-INF/bean-constructor-dependency-injection.xml"};
        //加载classpath下的资源
//        Resource resource = new ClassPathResource();
        //将资源进行转码
        //EncodedResource encodedResource = new EncodedResource(resource, "UTF-8");
        //XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        //
//        int beanNumbers = beanDefinitionReader.loadBeanDefinitions(encodedResource);
//        System.out.println("已加载 BeanDefinition 数量：" + beanNumbers);

        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        //
        beanDefinitionReader.loadBeanDefinitions(paths);
        //调用 SmartInitializingSingleton 接口实现，接口返回初始化完成后的 spring Bean
        beanFactory.preInstantiateSingletons();

        // 通过 Bean Id 和类型进行依赖查找
        User user = beanFactory.getBean("user", User.class);
        System.out.println(user);

        User superUser = beanFactory.getBean("superUser", User.class);
        System.out.println(superUser);

        UserHolder userHolder = beanFactory.getBean("userHolder", UserHolder.class);
        System.out.println("destory before :" + userHolder);

        //调用bean的销毁方法，但此时bean仅在spring中销毁，并未gc销毁
        beanFactory.destroyBean("userHolder", userHolder);
        System.out.println("destory after :" + userHolder);
    }
}
