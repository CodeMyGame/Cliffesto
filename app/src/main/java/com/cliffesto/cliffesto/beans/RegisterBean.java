package com.cliffesto.cliffesto.beans;

/**
 * Created by Kapil Gehlot on 1/21/2017.
 */

public class RegisterBean {
    public String name;
    public String email;
    public String mobile;
    public String college;
    public RegisterBean(String name, String email,String mobile,String college) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.college =college;

    }
}
