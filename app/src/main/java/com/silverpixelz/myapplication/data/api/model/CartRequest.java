
package com.silverpixelz.myapplication.data.api.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

public class CartRequest {

    public CartRequest(String contactNo, String corpcentre, String date, String emailId, String entryDateTime, String ipaddress, String message, String name, String srno, String type, String unit, String userId, String company) {
        ContactNo = contactNo;
        Corpcentre = corpcentre;
        Date = date;
        EmailId = emailId;
        EntryDateTime = entryDateTime;
        Ipaddress = ipaddress;
        Message = message;
        Name = name;
        Srno = srno;
        Type = type;
        Unit = unit;
        UserId = userId;
        Company = company;
    }
//
//    public CartRequest(String mContactNo, String mCorpcentre, String mDate,
//                       String mEmailId, String mEntryDateTime, String mIpaddress,
//                       String mMessage, String mName, String mSrno, String mType,
//                       String mUnit, String mUserId) {
//        this.ContactNo = mContactNo;
//        this.Corpcentre = mCorpcentre;
//        this.Date = mDate;
//        this.EmailId = mEmailId;
//        this.EntryDateTime = mEntryDateTime;
//        this.Ipaddress = mIpaddress;
//        this.Message = mMessage;
//        this.Name = mName;
//        this.Srno = mSrno;
//
//        this.Type = mType;
//        this.Unit = mUnit;
//        this.UserId = mUserId;
//    }

    private String ContactNo;
    private String Corpcentre;
    private String Date;
    private String EmailId;
    private String EntryDateTime;
    private String Ipaddress;
    private String Message;
    private String Name;
    private String Srno;
    private String Type;
    private String Unit;
    private String UserId;
    private String Company;
    public String getCompany() {
        return Company;
    }

    public void setCompany(String company) {
        Company = company;
    }


    public String getContactNo() {
        return ContactNo;
    }

    public void setContactNo(String contactNo) {
        ContactNo = contactNo;
    }

    public String getCorpcentre() {
        return Corpcentre;
    }

    public void setCorpcentre(String corpcentre) {
        Corpcentre = corpcentre;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getEmailId() {
        return EmailId;
    }

    public void setEmailId(String EmailId) {
        EmailId = EmailId;
    }

    public String getEntryDateTime() {
        return EntryDateTime;
    }

    public void setEntryDateTime(String EntryDateTime) {
        EntryDateTime = EntryDateTime;
    }

    public String getIpaddress() {
        return Ipaddress;
    }

    public void setIpaddress(String Ipaddress) {
        Ipaddress = Ipaddress;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        Message = Message;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSrno() {
        return Srno;
    }

    public void setSrno(String srno) {
        Srno = srno;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String Unit) {
        Unit = Unit;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String UserId) {
        UserId = UserId;
    }

}
