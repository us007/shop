package com.silverpixelz.myapplication.data.api.model.Country;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Dharmik Patel on 21-Aug-17.
 */

public class Country {

    public String getName() {
        return name;
    }

    public Country setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public Country setId(Integer id) {
        this.id = id;
        return this;
    }

    @SerializedName("country")
    @Expose
    private String name;
    @SerializedName("id")
    @Expose
    private Integer id;
}
