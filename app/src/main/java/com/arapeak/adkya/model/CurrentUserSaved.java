package com.arapeak.adkya.model;

import com.arapeak.adkya.model.login.User;

public class CurrentUserSaved  extends User {
    private  boolean isLogin=false;
    String country_name;
    Integer country_id;


    public Integer getCountry_id() {
        return country_id;
    }

    public void setCountry_id(Integer country_id) {
        this.country_id = country_id;
    }



    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setIsLogin(boolean login) {
        isLogin = login;
    }
}
