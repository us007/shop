package com.flipboard.myapplication.ui.cart;

import com.flipboard.myapplication.ui.base.Presenter;

/**
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
