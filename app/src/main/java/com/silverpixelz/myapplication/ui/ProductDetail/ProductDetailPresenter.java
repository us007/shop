package com.silverpixelz.myapplication.ui.ProductDetail;

import com.silverpixelz.myapplication.ui.base.Presenter;

/**
 * Created by Dharmik Patel on 19-Jun-17.
 */

public class ProductDetailPresenter implements Presenter<ProductDetailView> {

    private ProductDetailView productDetailView;


    @Override
    public void onAttach(ProductDetailView view) {
        productDetailView = view;
    }

    @Override
    public void onDetach() {
        productDetailView = null;
    }
}
