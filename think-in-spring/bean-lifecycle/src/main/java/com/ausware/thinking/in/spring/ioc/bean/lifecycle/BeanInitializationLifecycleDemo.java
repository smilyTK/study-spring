package com.ausware.thinking.in.spring.ioc.bean.lifecycle;

import com.ausware.thinking.in.spring.ioc.overview.entity.SuperUser;
import com.ausware.thinking.in.spring.ioc.overview.entity.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.util.ObjectUtils;

/**
 * Bean 实例初始化前，生命周期实例
 *
 * @author
 * @create 2020-04-29 9:18
 **/
public class BeanInitializationLifecycleDemo {

    public static void main(String[] args) {
        //
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        //添加bean后置处理器
        beanFactory.addBeanPostProcessor(new MyInstantiationAwarePostProcessor());
        //添加回调，解决@postConstruct注解驱动的问题
        beanFactory.addBeanPostProcessor(new CommonAnnotationBeanPostProcessor());
        //properties路径
        String[] paths = {"/META-INF/dependency-lookup-context.xml", "/META-INF/bean-constructor-dependency-injection.xml"};

        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        //
        beanDefinitionReader.loadBeanDefinitions(paths);

        // 通过 Bean Id 和类型进行依赖查找
        User user = beanFactory.getBean("user", User.class);
        System.out.println(user);

        User superUser = beanFactory.getBean("superUser", User.class);
        System.out.println(superUser);

        UserHolder userHolder = beanFactory.getBean("userHolder", UserHolder.class);
        System.out.println(userHolder);

    }

    /**
     * InstantiationAwareBeanPostProcessor -> BeanPostProcessor
     */
    static class MyInstantiationAwarePostProcessor implements InstantiationAwareBeanPostProcessor {

        @Override
        public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName){
            if(ObjectUtils.nullSafeEquals("superUser", beanName) && SuperUser.class.equals(beanClass)) {
                return new SuperUser();
            }
            //该后置处理器，返回null时，将进行默认后续的bean实例化
            //若返回非null时，将使用返回的Object，不再进行后续的bean实例化
            return null;
        }

        @Override
        public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
            //true : 进行设置
            //false : 表示跳过
            if(ObjectUtils.nullSafeEquals("user", beanName) && User.class.equals(bean.getClass())) {
                User user = (User) bean;
                user.setId(2L);
                user.setName("zhangsan...");
                // "user" 对象不允许属性赋值（植入）（配置元信息->属性值）
                return false;
            }
            return true;
        }

        // user 是跳过 bean 属性赋值(填入)
        // superUser 也是完全跳过 Bean 实例化（Bean 属性赋值（植入））
        // userHolder

        @Override
        public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
            if(ObjectUtils.nullSafeEquals("userHolder", beanName) && UserHolder.class.equals(bean.getClass())) {
                MutablePropertyValues propertyValues;
                if(pvs instanceof MutablePropertyValues) {
                    propertyValues = (MutablePropertyValues) pvs;
                } else {
                    propertyValues = new MutablePropertyValues();
                }
                //组装好之前，对属性进行修改
                propertyValues.addPropertyValue("number", 1);
                propertyValues.addPropertyValue("description", "this userHolder v2");
                return propertyValues;
            }
            return null;
        }

    }

}
