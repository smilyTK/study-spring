package com.ausware.thinking.in.spring.ioc.overview.container;

import com.ausware.thinking.in.spring.ioc.overview.entity.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

import java.util.Map;

/**
 * ioc容器demo之beanfactory
 *
 * @author
 * @create 2020-04-07 14:03
 **/
public class BeanFactoryAsIoCContainerDemo {
    public static void main(String[] args) {

        //创建 BeanFactory 容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        //创建xml读取器
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        //xml配置文件 classPath 路径
        String location = "classpath:/META-INF/dependency-injection-context.xml";
        //加载配置
        int beanDefinitionCount = reader.loadBeanDefinitions(location);
        System.out.println("Bean 定义加载的数量：" + beanDefinitionCount);
        //依赖查找集合对象
        lookupCollectionByType(beanFactory);
    }

    public static void lookupCollectionByType(BeanFactory beanFactory) {
        if(beanFactory instanceof ListableBeanFactory) {
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
            Map<String, User> userMap = (Map) listableBeanFactory.getBeansOfType(User.class);
            System.out.println("查找到的所有的User集合对象：" + userMap);
        }
    }
}
