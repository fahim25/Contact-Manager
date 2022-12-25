package com.contactmanager.model;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int uId;
    @NotBlank(message = "Name filed is required!")
    @Size(min = 4, max = 20, message = "Name should be between 4-20 letters")
    private String uName;
    @Column(unique = true)
    @NotBlank(message = "Please Enter a valid email.")
    private String uEmail;
    private String uImageUrl;
    @Column(length = 500)
    private String uAbout;
    private String uPassword;
    private String role;
    private boolean isActive;

//    @Override
//    public String toString() {
//        return "User{" +
//                "uId=" + uId +
//                ", uName='" + uName + '\'' +
//                ", uEmail='" + uEmail + '\'' +
//                ", uPassword='" + uPassword + '\'' +
//                ", role='" + role + '\'' +
//                ", isActive=" + isActive +
//                ", contactList=" + contactList +
//                ", uImageUrl='" + uImageUrl + '\'' +
//                ", uAbout='" + uAbout + '\'' +
//                '}';
//    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    private List<Contact> contactList = new ArrayList<>();

    public List<Contact> getContactList() {
        return contactList;
    }

    public void setContactList(List<Contact> contactList) {
        this.contactList = contactList;
    }

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getuEmail() {
        return uEmail;
    }

    public void setuEmail(String uEmail) {
        this.uEmail = uEmail;
    }

    public String getuPassword() {
        return uPassword;
    }

    public void setuPassword(String uPassword) {
        this.uPassword = uPassword;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getuImageUrl() {
        return uImageUrl;
    }

    public void setuImageUrl(String uImageUrl) {
        this.uImageUrl = uImageUrl;
    }

    public String getuAbout() {
        return uAbout;
    }

    public void setuAbout(String uAbout) {
        this.uAbout = uAbout;
    }

    public User(int uId, String uName, String uEmail, String uPassword, String role, boolean isActive, String uImageUrl, String uAbout) {
        this.uId = uId;
        this.uName = uName;
        this.uEmail = uEmail;
        this.uPassword = uPassword;
        this.role = role;
        this.isActive = isActive;
        this.uImageUrl = uImageUrl;
        this.uAbout = uAbout;
    }


    public User() {
    }
}
