package com.squareup.code.account;

/**
 * Created by Administrator on 2017/06/12 0012.
 */

public class User {

    /**
     * location :
     * sex : 0
     * img :
     * phone :
     * name :
     * age : 0
     * token :
     * account :
     */

    private String location;
    private int sex;
    private int uid;
    private String img;
    private String phone;
    private String name;
    private int age;
    private String token;
    private String account;
    int state = 0;

    public int getState() {
        return state;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getUid() {
        return uid;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
