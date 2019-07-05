package com.example.firstaidassistance;

public class User {
     private String userName,email,phone,photoUrl;



     public User(){

     }


    public User(String userName, String email,String photoUrl) {
        this.userName = userName;
        this.email = email;
        this.phone = phone;
        this.photoUrl = photoUrl;

    }


    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


}
