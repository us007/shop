package com.silverpixelz.myapplication.data.api.model.Pdf;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pdf {

    public Integer getId() {
        return id;
    }

    public Pdf setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getPdf_name() {
        return pdf_name;
    }

    public Pdf setPdf_name(String pdf_name) {
        this.pdf_name = pdf_name;
        return this;
    }

    public String getDocument_upload() {
        return document_upload;
    }

    public Pdf setDocument_upload(String document_upload) {
        this.document_upload = document_upload;
        return this;
    }

    public String getCreated_on() {
        return created_on;
    }

    public Pdf setCreated_on(String created_on) {
        this.created_on = created_on;
        return this;
    }

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("pdf_name")
    @Expose
    private String pdf_name;

    @SerializedName("document_upload")
    @Expose
    private String document_upload;

    @SerializedName("created_on")
    @Expose
    private String created_on;


}
