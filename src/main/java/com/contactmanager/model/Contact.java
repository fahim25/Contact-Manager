package com.contactmanager.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cId;

//    @NotBlank(message = "Name filed is required!")
    private String cName;
    private String cSurName;
    private String cWork;
    private String cEmail;
//    @NotBlank(message = "Mobile number is required!")
    private String cMobile;
    private String cImageUrl;
    @Column(length = 1000)
    private String cAbout;

    @ManyToOne
    private User user;

    @Override
    public String toString() {
        return "Contact{" +
                "cId=" + cId +
                ", cName='" + cName + '\'' +
                ", cSurName='" + cSurName + '\'' +
                ", cWork='" + cWork + '\'' +
                ", cEmail='" + cEmail + '\'' +
                ", cMobile='" + cMobile + '\'' +
                ", cImageUrl='" + cImageUrl + '\'' +
                ", cAbout='" + cAbout + '\'' +
                ", user=" + user +
                '}';
    }



    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getcId() {
        return cId;
    }

    public void setcId(int cId) {
        this.cId = cId;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcSurName() {
        return cSurName;
    }

    public void setcSurName(String cSurName) {
        this.cSurName = cSurName;
    }

    public String getcWork() {
        return cWork;
    }

    public void setcWork(String cWork) {
        this.cWork = cWork;
    }

    public String getcEmail() {
        return cEmail;
    }

    public void setcEmail(String cEmail) {
        this.cEmail = cEmail;
    }

    public String getcMobile() {
        return cMobile;
    }

    public void setcMobile(String cMobile) {
        this.cMobile = cMobile;
    }

    public String getcImageUrl() {
        return cImageUrl;
    }

    public void setcImageUrl(String cImageUrl) {
        this.cImageUrl = cImageUrl;
    }

    public String getcAbout() {
        return cAbout;
    }

    public void setcAbout(String cAbout) {
        this.cAbout = cAbout;
    }

    public Contact(int cId, String cName, String cSurName, String cWork, String cEmail, String cMobile, String cImageUrl, String cAbout) {
        this.cId = cId;
        this.cName = cName;
        this.cSurName = cSurName;
        this.cWork = cWork;
        this.cEmail = cEmail;
        this.cMobile = cMobile;
        this.cImageUrl = cImageUrl;
        this.cAbout = cAbout;
    }

    public Contact() {
    }
}
