package com.silverpixelz.myapplication.ui.cart;

import com.silverpixelz.myapplication.ui.base.View;

/**
 * Created by Dharmik Patel on 20-Jun-17.
 */

public interface CartView extends View {
    void ShowProgress();

    void HideProgress();

    void ResponseError(String title,String message,int i);
}
