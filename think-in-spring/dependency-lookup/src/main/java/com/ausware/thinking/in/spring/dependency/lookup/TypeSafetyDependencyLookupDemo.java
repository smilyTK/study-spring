package com.ausware.thinking.in.spring.dependency.lookup;

import com.ausware.thinking.in.spring.ioc.overview.entity.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 类型安全，依赖查找实例
 *
 * @author
 * @create 2020-04-13 8:35
 **/
public class TypeSafetyDependencyLookupDemo {

    public static void main(String[] args) {

        //创建 BeanFactory 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        //将当前类 ObjectProviderDemo 作为配置类
        applicationContext.register(HierarchicalDependencyLookupDemo.class);
        //启用应用上下文
        applicationContext.refresh();


        //演示 BeanFactory#getBean 方法的安全性，结论：不安全
        displayBeanFactoryGetBean(applicationContext);
        //演示 ObjectFactory#getObject 方法的安全性，结论：不安全
        displayObjectFactoryGetBean(applicationContext);
        //演示 ObjectProvider#getObject 方法的安全性，结论：安全
        displayObjectProviderIfAvailable(applicationContext);

        //演示 ListableBeanFactory#getBeansOfType 方法的安全性
        displayListableBeanFactoryGetBeansOfType(applicationContext);
        //演示 ObjectProvider StreamOps 操作的安全性，结论：安全
        displayObjectProviderStreamOps(applicationContext);

        //结论： 单一类型和集合类型使用依赖查找，线程不安全
        //      建议使用ObjectProvider进行依赖查找

        //关闭应用上下文
        applicationContext.close();
    }

    /**
     * 演示 BeanFactory#getBean 方法的安全性
     * @param beanFactory
     */
    public static void displayBeanFactoryGetBean(BeanFactory beanFactory) {
        printBeansExxception("BeanFactory#getBean", () -> beanFactory.getBean(User.class));
    }

    /**
     * 演示 ObjectFactory#getObject 方法的安全性
     * @param applicationContext
     */
    public static void displayObjectFactoryGetBean(AnnotationConfigApplicationContext applicationContext) {
        ObjectFactory<User> objectFactory = applicationContext.getBeanProvider(User.class);
        printBeansExxception("ObjectFactory#getObject", () -> objectFactory.getObject());
    }

    /**
     * 演示 ObjectProvider#getObject 方法的安全性
     * @param applicationContext
     */
    public static void displayObjectProviderIfAvailable(AnnotationConfigApplicationContext applicationContext) {
        ObjectProvider<User> objectProvider = applicationContext.getBeanProvider(User.class);
        printBeansExxception("ObjectProvider#getObject", () -> objectProvider.getObject());
    }

    /**
     * 演示 ListableBeanFactory#getBeansOfType 方法的安全性
     * @param listableBeanFactory
     */
    public static void displayListableBeanFactoryGetBeansOfType(ListableBeanFactory listableBeanFactory) {
        printBeansExxception("ListableBeanFactory#getBeansOfType", () -> listableBeanFactory.getBeansOfType(User.class));
    }

    /**
     * 演示 ObjectProviderStreamOps 操作的安全性
     * @param applicationContext
     */
    public static void displayObjectProviderStreamOps(AnnotationConfigApplicationContext applicationContext) {
        ObjectProvider<User> objectProvider = applicationContext.getBeanProvider(User.class);
        printBeansExxception("ObjectProviderStreamOps", () -> objectProvider.forEach(System.out::println));
    }

    /**
     * 打印确实bean异常
     * @param runnable
     */
    private  static void printBeansExxception(String source, Runnable runnable) {
        System.err.println("==========================");
        System.err.println("source from : " + source);
        try {
            runnable.run();
        } catch (BeansException e) {
            e.printStackTrace();
        }

    }

}
