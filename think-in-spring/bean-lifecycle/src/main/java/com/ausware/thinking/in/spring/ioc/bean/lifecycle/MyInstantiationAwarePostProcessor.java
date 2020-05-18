package com.ausware.thinking.in.spring.ioc.bean.lifecycle;

import com.ausware.thinking.in.spring.ioc.overview.entity.SuperUser;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.util.ObjectUtils;

/**
 * 自定义实例化前回调后置处理器
 *
 * @author
 * @create 2020-05-09 6:54
 **/
public class MyInstantiationAwarePostProcessor implements InstantiationAwareBeanPostProcessor {

    /**
     * 实例化前回调，若返回对象直接覆盖bean实例化对象
     */
    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        //bean实例化前回调
        if(ObjectUtils.nullSafeEquals("superUser", beanName) && SuperUser.class.equals(beanClass)) {
            System.out.println("postProcessBeforeInstantiation() ...");
            return new SuperUser();
        }
        //该后置处理器，返回null时，将进行默认后续的bean实例化
        //若返回非null时，将使用返回的Object，不再进行后续的bean实例化
        return null;
    }

    /**
     * 实例化时，属性设置
     */
    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        //true : 进行设置
        //false : 表示跳过
        if(ObjectUtils.nullSafeEquals("userHolder", beanName) && UserHolder.class.equals(bean.getClass())) {
            UserHolder userHolder = (UserHolder) bean;
            userHolder.setDescription("the user holder V1");
            // "user" 对象不允许属性赋值（植入）（配置元信息->属性值）
            System.out.println("postProcessAfterInstantiation() ..." + userHolder.getDescription());
            return false;
        }
        return true;
    }

    // user 是跳过 bean 属性赋值(填入)
    // superUser 也是完全跳过 Bean 实例化（Bean 属性赋值（植入））
    // userHolder

    @Override
    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        if(bean.getClass().equals(UserHolder.class) && ObjectUtils.nullSafeEquals("userHolder", beanName)) {
            MutablePropertyValues propertyValues;
            if(pvs instanceof MutablePropertyValues) {
                propertyValues = (MutablePropertyValues) pvs;
            } else {
                propertyValues = new MutablePropertyValues();
            }
            propertyValues.addPropertyValue("number", "2");
            // 如果存在 "description" 属性配置的话
            if (propertyValues.contains("description")) {
                // PropertyValue value 是不可变的
//                    PropertyValue propertyValue = propertyValues.getPropertyValue("description");
                propertyValues.removePropertyValue("description");
                propertyValues.addPropertyValue("description", "The user holder V2");
            }
            return propertyValues;
        }
        return null;
    }


    /**
     * 实例化完成前回调
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if(ObjectUtils.nullSafeEquals("userHolder", beanName) && bean.getClass().equals(UserHolder.class)) {
            UserHolder userHolder = (UserHolder) bean;
            userHolder.setDescription("the user holder V3");
            System.out.println("postProcessBeforeInitialization() ..." + userHolder.getDescription());
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        //postProcessAfterInitialization -> afterSingletonsInstantiated
        if(ObjectUtils.nullSafeEquals("userHolder", beanName) && bean.getClass().equals(UserHolder.class)) {
            UserHolder userHolder = (UserHolder) bean;
            userHolder.setDescription("this user holder V7");
            System.out.println("postProcessAfterInitialization() = " + userHolder.getDescription());
        }
        return bean;
    }

}
