package com.ausware.thinking.in.spring.ioc.bean.lifecycle;

import com.ausware.thinking.in.spring.ioc.overview.entity.User;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.PropertiesBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;

/**
 * Bean 元信息配置实例
 * 使用 properties 配置加载spring
 *
 * @author
 * @create 2020-04-22 8:53
 **/
public class BeanMetadataConfigurationDemo {

    public static void main(String[] args) {
        //创建默认的 BeanFactory 实现
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        //加载properties格式的spring配置
        PropertiesBeanDefinitionReader beanDefinitionReader = new PropertiesBeanDefinitionReader(beanFactory);
        //properties路径
        String path = "/META-INF/user.properties";
        //加载classpath下的资源
        Resource resource = new ClassPathResource(path);
        //将资源进行转码
        EncodedResource encodedResource = new EncodedResource(resource, "UTF-8");
        //加载BeanDefintion
        int num = beanDefinitionReader.loadBeanDefinitions(encodedResource);
        //依赖查找
        User user = beanFactory.getBean(User.class);
        System.out.println("加载BeanDefinition数量：" + num);
        System.out.println("依赖查找：" + user);
    }

}
