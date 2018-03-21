package com.silverpixelz.myapplication.data.db.apihelper;

import android.content.Context;
import android.widget.Toast;

import com.silverpixelz.myapplication.data.db.model.NotificationDB;
import com.silverpixelz.myapplication.data.db.model.ProductDB;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by Dharmik Patel on 19-Jun-17.
 */

public class RealmHelper {

    private Realm mRealm;
    private Context mContext;

    public RealmHelper(Context context) {
        mContext = context;
        mRealm = Realm.getDefaultInstance();
    }

    public void RealmClose() {
        mRealm.close();
    }

    public Boolean SaveProduct(int product_id, String product_name, String product_no,
                               String product_image, String product_qty,
                               String printing_option, String print_location) {
        Boolean flag = true;
        mRealm.beginTransaction();
        RealmResults<ProductDB> results = mRealm.where(ProductDB.class)
                .equalTo("product_id", product_id)
                .findAll();
        if (results.size() > 0) {
            Toast.makeText(mContext, "Product already in cart", Toast.LENGTH_SHORT).show();
            flag = false;
        } else {
            ProductDB productDB = mRealm.createObject(ProductDB.class);
            productDB.setProduct_id(product_id);
            productDB.setProduct_name(product_name);
            productDB.setProduct_no(product_no);
            productDB.setProduct_image(product_image);
            productDB.setProduct_qty(product_qty);
            productDB.setProduct_printing_option(printing_option);
            productDB.setProduct_print_location(print_location);
            Toast.makeText(mContext, "Product successfully added in cart", Toast.LENGTH_SHORT).show();
            flag = true;
        }
        mRealm.commitTransaction();
        return flag;
    }

    public RealmResults<ProductDB> GetProduct() {
        return mRealm.where(ProductDB.class).findAll();
    }

    public int GetProductsCount() {
        RealmResults<ProductDB> results = mRealm.where(ProductDB.class).findAll();
        return results.size();
    }

    public void DeleteProduct(int id) {
        mRealm.beginTransaction();
        RealmResults<ProductDB> results = mRealm.where(ProductDB.class)
                .equalTo("product_id", id)
                .findAll();
        results.deleteFirstFromRealm();
        mRealm.commitTransaction();
    }

    public void DeleteAllProduct() {
        mRealm.beginTransaction();
        RealmResults<ProductDB> results = mRealm.where(ProductDB.class)
                .findAll();
        results.deleteAllFromRealm();
        mRealm.commitTransaction();
    }

    public void SaveNotification(String mNotificationTitle, String mNotificationMessage,
                                 String mProductId, String mImage, String mTime,
                                 Boolean mIsRead,String mType) {
        mRealm.beginTransaction();
        RealmResults<NotificationDB> results = mRealm.where(NotificationDB.class)
                .equalTo("mProductId", mProductId)
                .findAll();
        if (results.size() > 0) {
         //   Toast.makeText(mContext, "Product already in cart", Toast.LENGTH_SHORT).show();
        } else {
            NotificationDB productDB = mRealm.createObject(NotificationDB.class);
            productDB.setmNotificationTitle(mNotificationTitle);
            productDB.setmNotificationMessage(mNotificationMessage);
            productDB.setmProductId(mProductId);
            productDB.setmImage(mImage);
            productDB.setmTime(mTime);
            productDB.setmType(mType);
            productDB.setmIsRead(mIsRead);
        }
        mRealm.commitTransaction();
    }

    public void DeleteAllNotification() {
        mRealm.beginTransaction();
        RealmResults<NotificationDB> results = mRealm.where(NotificationDB.class)
                .findAll();
        results.deleteAllFromRealm();
        mRealm.commitTransaction();
    }

    public List<NotificationDB> GetAllNotification() {
        return mRealm.where(NotificationDB.class).findAllSorted("mTime", Sort.DESCENDING);
    }

    public List<NotificationDB> GetUnreadNotification() {
        return mRealm.where(NotificationDB.class)
                .equalTo("mIsRead", false)
                .findAll();
    }

    public List<NotificationDB> GetReadNotification() {
        return mRealm.where(NotificationDB.class)
                .equalTo("mIsRead", true)
                .findAll();
    }

    public void updateToRead(String id) {
        NotificationDB toEdit = mRealm.where(NotificationDB.class)
                .equalTo("mProductId", id).findFirst();
        mRealm.beginTransaction();
        toEdit.setmIsRead(true);
        mRealm.commitTransaction();
    }
}
