package com.example.bijay.work1.firedatabase;

import com.google.firebase.database.Exclude;

/**
 * Created by Bijay on 4/17/2018.
 */

public class Model {

    String name,email,mobile,password,confirm,address,id,mimageurl;


    public Model(){

    }



    public Model(String name, String email, String mobile, String password, String confirm, String address,String id,String mimageurl) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.password = password;
        this.confirm = confirm;
        this.address = address;
        this.id=id;
        this.mimageurl=mimageurl;
    }




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMimageurl() {
        return mimageurl;
    }

    public void setMimageurl(String mimageurl) {
        this.mimageurl = mimageurl;
    }
}
