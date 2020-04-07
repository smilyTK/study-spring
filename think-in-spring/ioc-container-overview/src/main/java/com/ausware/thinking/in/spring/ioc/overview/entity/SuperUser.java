package com.ausware.thinking.in.spring.ioc.overview.entity;

import com.ausware.thinking.in.spring.ioc.overview.annotation.Super;

/**
 * 超级user
 *
 * @author
 * @create 2020-04-07 13:03
 **/
@Super
public class SuperUser extends User {

    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "SuperUser{" +
                "address='" + address + '\'' +
                "} " + super.toString();
    }

}
