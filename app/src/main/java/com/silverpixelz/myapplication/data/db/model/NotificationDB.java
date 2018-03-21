package com.silverpixelz.myapplication.data.db.model;

import io.realm.RealmObject;

/**
 * Created by Dharmik Patel on 22-Nov-17.
 */

public class NotificationDB extends RealmObject {

    public NotificationDB() {
    }

    public NotificationDB(String mNotificationTitle, String mNotificationMessage, String mProductId, String mImage, String mTime, Boolean mIsRead,String mType) {
        this.mNotificationTitle = mNotificationTitle;
        this.mNotificationMessage = mNotificationMessage;
        this.mProductId = mProductId;
        this.mImage = mImage;
        this.mTime = mTime;
        this.mIsRead = mIsRead;
        this.mType = mType;
    }

    public String getmNotificationTitle() {
        return mNotificationTitle;
    }

    public NotificationDB setmNotificationTitle(String mNotificationTitle) {
        this.mNotificationTitle = mNotificationTitle;
        return this;
    }

    public String getmNotificationMessage() {
        return mNotificationMessage;
    }

    public NotificationDB setmNotificationMessage(String mNotificationMessage) {
        this.mNotificationMessage = mNotificationMessage;
        return this;
    }

    public String getmProductId() {
        return mProductId;
    }

    public NotificationDB setmProductId(String mProductId) {
        this.mProductId = mProductId;
        return this;
    }

    public String getmImage() {
        return mImage;
    }

    public NotificationDB setmImage(String mImage) {
        this.mImage = mImage;
        return this;
    }

    public String getmTime() {
        return mTime;
    }

    public NotificationDB setmTime(String mTime) {
        this.mTime = mTime;
        return this;
    }

    public Boolean getmIsRead() {
        return mIsRead;
    }

    public NotificationDB setmIsRead(Boolean mIsRead) {
        this.mIsRead = mIsRead;
        return this;
    }

    private String mNotificationTitle = "";
    private String mNotificationMessage = "";
    private String mProductId = "";
    private String mImage = "";
    private String mTime = "";
    private Boolean mIsRead = false;
    private String mType;

    public String getmType() {
        return mType;
    }

    public NotificationDB setmType(String mType) {
        this.mType = mType;
        return this;
    }
}
