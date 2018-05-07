package com.flipboard.myapplication.events;

import com.flipboard.myapplication.data.api.model.Product.Product;

/**
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
