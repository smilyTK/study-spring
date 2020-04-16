package com.ausware.thinking.in.spring.ioc.dependency.injection;

import com.ausware.thinking.in.spring.ioc.overview.entity.User;

/**
 * 用户处理类
 *
 * @author
 * @create 2020-04-16 8:54
 **/
public class UserHolder {

    private User user;

    public UserHolder() {
    }

    public UserHolder(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserHolder{" +
                "user=" + user +
                '}';
    }
}
