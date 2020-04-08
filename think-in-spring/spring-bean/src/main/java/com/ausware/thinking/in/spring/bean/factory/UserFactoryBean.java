package com.ausware.thinking.in.spring.bean.factory;

import com.ausware.thinking.in.spring.ioc.overview.entity.User;
import org.springframework.beans.factory.FactoryBean;

/**
 * 工厂bean的实现
 *
 * @author
 * @create 2020-04-08 9:05
 **/
public class UserFactoryBean implements FactoryBean {
    @Override
    public Object getObject() throws Exception {
        return User.createUser();
    }

    @Override
    public Class<?> getObjectType() {
        return User.class;
    }
}
