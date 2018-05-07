package com.flipboard.myapplication.data.api.model.printOption;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 */

public class PrintOption {

    public String getName() {
        return name;
    }

    public PrintOption setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public PrintOption setId(Integer id) {
        this.id = id;
        return this;
    }

    @SerializedName("print_type")
    @Expose
    private String name;
    @SerializedName("id")
    @Expose
    private Integer id;
}
