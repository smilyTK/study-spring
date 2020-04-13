package com.ausware.thinking.in.spring.dependency.lookup;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * 使用ObjectProvider进行依赖查找实例
 * 单一类型依赖查找
 * @author
 * @create 2020-04-10 8:58
 **/
public class ObjectProviderDemo {

    public static void main(String[] args) {

        //创建 BeanFactory 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        //将当前类 ObjectProviderDemo 作为配置类
        applicationContext.register(ObjectProviderDemo.class);
        //启用应用上下文
        applicationContext.refresh();

        //单一类型依赖查找
        lookupByObjectProvider(applicationContext);

        //关闭应用上下文
        applicationContext.close();
    }

    @Bean
    public String helloworld() {
        return "helloworld";
    }


    public static void lookupByObjectProvider(AnnotationConfigApplicationContext applicationContext) {
        ObjectProvider<String> objectProvider = applicationContext.getBeanProvider(String.class);
        System.out.println(objectProvider.getObject());
    }

}
