package com.ausware.thinking.in.spring.bean.factory;

import com.ausware.thinking.in.spring.ioc.overview.entity.User;

/**
 * 工厂类接口
 *
 * @author
 * @create 2020-04-08 8:45
 **/
public interface UserFactory {

    /**
     * 工厂方法，创建用户对象
     * @return
     */
    default User createUser() {
        return User.createUser();
    };

}
