package com.ausware.thinking.in.spring.ioc.bean.lifecycle;

import com.ausware.thinking.in.spring.ioc.overview.entity.User;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;

/**
 * BeanDefinition合并阶段示例
 *
 * @author
 * @create 2020-04-24 9:01
 **/
public class MergedBeanDefinitionDemo {

    public static void main(String[] args) {
        //
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        //properties路径
        String path = "/META-INF/dependency-lookup-context.xml";
        //加载classpath下的资源
        Resource resource = new ClassPathResource(path);
        //将资源进行转码
        EncodedResource encodedResource = new EncodedResource(resource, "UTF-8");

        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        //
        int beanNumbers = beanDefinitionReader.loadBeanDefinitions(encodedResource);
        System.out.println("已加载 BeanDefinition 数量：" + beanNumbers);
        // 通过 Bean Id 和类型进行依赖查找
        User user = beanFactory.getBean("user", User.class);
        System.out.println(user);

        User superUser = beanFactory.getBean("superUser", User.class);
        System.out.println(superUser);
    }

}
