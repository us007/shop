package com.flipboard.myapplication.ui.cart;

import com.flipboard.myapplication.ui.base.View;

/**
 */

public interface CartView extends View {
    void ShowProgress();

    void HideProgress();

    void ResponseError(String title,String message,int i);
}
