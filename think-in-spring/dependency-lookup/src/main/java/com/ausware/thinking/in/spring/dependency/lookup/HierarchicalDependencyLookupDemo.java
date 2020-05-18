package com.ausware.thinking.in.spring.dependency.lookup;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 层次性依赖查找实例
 *
 * @author
 * @create 2020-04-10 9:25
 **/
public class HierarchicalDependencyLookupDemo {

    public static void main(String[] args) {

        //创建 BeanFactory 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        //将当前类 ObjectProviderDemo 作为配置类
        applicationContext.register(HierarchicalDependencyLookupDemo.class);
        //启用应用上下文
        applicationContext.refresh();

        //1. 获取 HierarchicalBeanFactory <- ConfigurableBeanFactory <- ConfigurableListableBeanFactory

    }

    public static void getParentBeanFactory() {

    }
}
