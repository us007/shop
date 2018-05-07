package com.flipboard.myapplication.ui.ProductDetail;

import com.flipboard.myapplication.ui.base.Presenter;

/**
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
