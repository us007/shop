package com.silverpixelz.myapplication.ui.cart;

import com.silverpixelz.myapplication.ui.base.Presenter;

/**
 * Created by Dharmik Patel on 20-Jun-17.
 */

public class CartPresenter implements Presenter<CartView> {

    private CartView cartView;

    @Override
    public void onAttach(CartView view) {
        cartView = view;
    }

    @Override
    public void onDetach() {
        cartView = null;
    }
}
