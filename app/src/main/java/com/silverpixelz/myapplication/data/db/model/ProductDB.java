package com.silverpixelz.myapplication.data.db.model;

import io.realm.RealmObject;

/**
 * Created by Dharmik Patel on 19-Jun-17.
 */

public class ProductDB extends RealmObject {

    public ProductDB(int product_id, String product_name, String product_no, String product_image, String product_qty, String product_printing_option, String product_print_location) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.product_no = product_no;
        this.product_image = product_image;
        this.product_qty = product_qty;
        this.product_printing_option = product_printing_option;
        this.product_print_location = product_print_location;
    }

    public int getProduct_id() {
        return product_id;
    }

    public ProductDB setProduct_id(int product_id) {
        this.product_id = product_id;
        return this;
    }

    public String getProduct_name() {
        return product_name;
    }

    public ProductDB setProduct_name(String product_name) {
        this.product_name = product_name;
        return this;
    }

    public String getProduct_no() {
        return product_no;
    }

    public ProductDB setProduct_no(String product_no) {
        this.product_no = product_no;
        return this;
    }

    public String getProduct_image() {
        return product_image;
    }

    public ProductDB setProduct_image(String product_image) {
        this.product_image = product_image;
        return this;
    }

    public String getProduct_qty() {
        return product_qty;
    }

    public ProductDB setProduct_qty(String product_qty) {
        this.product_qty = product_qty;
        return this;
    }

    public String getProduct_printing_option() {
        return product_printing_option;
    }

    public ProductDB setProduct_printing_option(String product_printing_option) {
        this.product_printing_option = product_printing_option;
        return this;
    }

    public String getProduct_print_location() {
        return product_print_location;
    }

    public ProductDB setProduct_print_location(String product_print_location) {
        this.product_print_location = product_print_location;
        return this;
    }

    private String product_image;
    private String product_qty;
    private String product_printing_option;
    private String product_print_location;
    private int product_id;
    private String product_name;
    private String product_no;

    public ProductDB(){

    }
}
