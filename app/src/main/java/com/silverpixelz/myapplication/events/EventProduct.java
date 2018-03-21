package com.silverpixelz.myapplication.events;

import com.silverpixelz.myapplication.data.api.model.Product.Product;

/**
 * Created by Dharmik Patel on 19-Jun-17.
 */

public class EventProduct {

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public EventProduct(Product product) {
        this.product = product;
    }

    private Product product;
}
