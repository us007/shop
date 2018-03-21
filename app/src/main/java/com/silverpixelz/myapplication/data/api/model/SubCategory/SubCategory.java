package com.silverpixelz.myapplication.data.api.model.SubCategory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubCategory {

    @SerializedName("subcatname")
    @Expose
    private String subcatname;
    @SerializedName("subimage")
    @Expose
    private Object subimage;
    @SerializedName("subcatbanner")
    @Expose
    private Object subcatbanner;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("cat_id")
    @Expose
    private Integer catId;
    @SerializedName("rank")
    @Expose
    private Integer rank;
    @SerializedName("count")
    @Expose
    private Integer count;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getSubcatname() {
        return subcatname;
    }

    public void setSubcatname(String subcatname) {
        this.subcatname = subcatname;
    }

    public Object getSubimage() {
        return subimage;
    }

    public void setSubimage(Object subimage) {
        this.subimage = subimage;
    }

    public Object getSubcatbanner() {
        return subcatbanner;
    }

    public void setSubcatbanner(Object subcatbanner) {
        this.subcatbanner = subcatbanner;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCatId() {
        return catId;
    }

    public void setCatId(Integer catId) {
        this.catId = catId;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

}
