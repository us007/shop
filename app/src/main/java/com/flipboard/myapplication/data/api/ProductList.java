package com.flipboard.myapplication.data.api;

/**
 */

public class ProductList {

    public String getProduct_id() {
        return product_id;
    }

    public ProductList setProduct_id(String product_id) {
        this.product_id = product_id;
        return this;
    }

    public String getProduct_name() {
        return product_name;
    }

    public ProductList setProduct_name(String product_name) {
        this.product_name = product_name;
        return this;
    }

    public String getProduct_no() {
        return product_no;
    }

    public ProductList setProduct_no(String product_no) {
        this.product_no = product_no;
        return this;
    }

    public String getProduct_image() {
        return product_image;
    }

    public ProductList setProduct_image(String product_image) {
        this.product_image = product_image;
        return this;
    }

    public String getProduct_qty() {
        return product_qty;
    }

    public ProductList setProduct_qty(String product_qty) {
        this.product_qty = product_qty;
        return this;
    }

    public String getProduct_printing_option() {
        return product_printing_option;
    }

    public ProductList setProduct_printing_option(String product_printing_option) {
        this.product_printing_option = product_printing_option;
        return this;
    }

    public String getProduct_print_location() {
        return product_print_location;
    }

    public ProductList setProduct_print_location(String product_print_location) {
        this.product_print_location = product_print_location;
        return this;
    }

    private String product_image;
    private String product_qty;
    private String product_printing_option;
    private String product_print_location;
    private String product_id;

    public String getSrno() {
        return srno;
    }

    public ProductList setSrno(String srno) {
        this.srno = srno;
        return this;
    }

    private String srno;

    public String getId() {
        return id;
    }

    public ProductList setId(String id) {
        this.id = id;
        return this;
    }

    private String id;
    private String product_name;
    private String product_no;
}
