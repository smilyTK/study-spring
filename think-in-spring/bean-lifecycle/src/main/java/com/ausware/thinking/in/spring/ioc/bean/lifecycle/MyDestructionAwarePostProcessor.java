package com.ausware.thinking.in.spring.ioc.bean.lifecycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;
import org.springframework.util.ObjectUtils;

/**
 * Spring Bean销毁前阶段
 *
 * @author
 * @create 2020-05-12 6:39
 **/
public class MyDestructionAwarePostProcessor implements DestructionAwareBeanPostProcessor {
    @Override
    public void postProcessBeforeDestruction(Object bean, String beanName) throws BeansException {
        //销毁前对bean进行修改
        if(ObjectUtils.nullSafeEquals("userHolder", beanName) && UserHolder.class.equals(bean.getClass())) {
            UserHolder userHolder = (UserHolder) bean;
            // afterSingletonsInstantiated() -> postProcessBeforeDestruction()
            userHolder.setDescription("the user holder V9");
            // "user" 对象不允许属性赋值（植入）（配置元信息->属性值）
            System.out.println("postProcessBeforeDestruction() ..." + userHolder.getDescription());
        }
    }
}
