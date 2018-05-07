
package com.flipboard.myapplication.data.api.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

public class SubscribeRequest {

    private String Company;
    private String Country;
    private String Email;
    private int Id;
    private String Name;

    public String getEvent_company() {
        return event_company;
    }

    public SubscribeRequest setEvent_company(String event_company) {
        this.event_company = event_company;
        return this;
    }

    public String getAdvertising() {
        return advertising;
    }

    public SubscribeRequest setAdvertising(String advertising) {
        this.advertising = advertising;
        return this;
    }

    private String event_company;
    private String advertising;

    public SubscribeRequest(String mCompany, String mCountry, String mEmail, int mId, String mName, String mTelephone,String mevent, String madvertising) {
        this.Company = mCompany;
        this.Country = mCountry;
        this.Email = mEmail;
        this.Id = mId;
        this.Name = mName;
        this.Telephone = mTelephone;
        this.event_company = mevent;
        this.advertising = madvertising;
    }

    private String Telephone;

    public String getCompany() {
        return Company;
    }

    public void setCompany(String company) {
        Company = company;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getTelephone() {
        return Telephone;
    }

    public void setTelephone(String telephone) {
        Telephone = telephone;
    }

}
