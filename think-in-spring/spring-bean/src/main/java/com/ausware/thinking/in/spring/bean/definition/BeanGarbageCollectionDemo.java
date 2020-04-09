package com.ausware.thinking.in.spring.bean.definition;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * bean的垃圾回收
 *
 * @author
 * @create 2020-04-09 8:44
 **/
public class BeanGarbageCollectionDemo {

    public static void main(String[] args) throws InterruptedException {
        //创建BeanFactory
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        //注册ConfigurationClass
        applicationContext.register(BeanInitializationDemo.class);
        //启用应用上下文
        applicationContext.refresh();
        //关闭Spring应用上下文
        applicationContext.close();
        Thread.sleep(5000);
        //强制触发GC
        System.gc();
        Thread.sleep(5000);
    }

}
