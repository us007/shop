package com.silverpixelz.myapplication.notification;

/**
 * Created by Dharmik Patel on 21-Nov-17.
 */

import android.content.Intent;
import android.util.Log;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.silverpixelz.myapplication.data.db.apihelper.RealmHelper;
import com.silverpixelz.myapplication.data.db.model.NotificationDB;
import com.silverpixelz.myapplication.events.EventNotification;
import com.silverpixelz.myapplication.ui.notification.NotificationActivity;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by francesco on 13/09/16.
 */
public class FireMsgService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (remoteMessage.getData().size() > 0) {
            try {
                Log.e("Notification Json", "" + remoteMessage.getData().toString());
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                sendPushNotification(json);
            } catch (Exception e) {
                Log.e("Notification Json", "Exception: " + e.getMessage());
            }
        }
    }

    private void sendPushNotification(JSONObject json) {
        try {
            JSONObject data = json.getJSONObject("data");

            String title = data.getString("title");
            String message = data.getString("message");
            String imageUrl = data.getString("image");
            String productId = data.getString("Id");
            String type = data.getString("type");

            MyNotificationManager mNotificationManager = new MyNotificationManager(getApplicationContext());
            Intent intent = new Intent(getApplicationContext(), NotificationActivity.class);

            new RealmHelper(this).SaveNotification(title, message, productId, imageUrl, getDateTime(), false, type);

            if (type.equals("product")) {
                EventBus.getDefault().postSticky(new EventNotification(new NotificationDB(title, message, productId, imageUrl, getDateTime(), false, type)));
                if (imageUrl.equals("null") || imageUrl.equals("")) {
                    mNotificationManager.showSmallNotification(title, message, intent);
                } else {
                    mNotificationManager.showBigNotification(title, message, imageUrl, intent);
                }
            } else {
                EventBus.getDefault().postSticky(new EventNotification(new NotificationDB(title, message, productId, imageUrl, getDateTime(), false, type)));
                if (imageUrl.equals("null") || imageUrl.equals("")) {
                    mNotificationManager.showSmallNotification(title, message, intent);
                } else {
                    mNotificationManager.showBigNotification(title, message, imageUrl, intent);
                }
            }

        } catch (JSONException e) {
            // Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            //  Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
