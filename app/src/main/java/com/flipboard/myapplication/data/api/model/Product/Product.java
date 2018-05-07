package com.flipboard.myapplication.data.api.model.Product;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {

    @SerializedName("prod_num")
    @Expose
    private String prodNum;
    @SerializedName("prodname")
    @Expose
    private String prodname;
    @SerializedName("meta_title")
    @Expose
    private Object metaTitle;
    @SerializedName("meta_desc")
    @Expose
    private Object metaDesc;
    @SerializedName("prod_image")
    @Expose
    private String prodImage;
    @SerializedName("prod_link")
    @Expose
    private Object prodLink;
    @SerializedName("prod_manual")
    @Expose
    private Object prodManual;
    @SerializedName("offer_text")
    @Expose
    private Object offerText;
    @SerializedName("catalogue_name")
    @Expose
    private String catalogueName;
    @SerializedName("cat_model_num")
    @Expose
    private String catModelNum;
    @SerializedName("color1")
    @Expose
    private Object color1;
    @SerializedName("color2")
    @Expose
    private Object color2;
    @SerializedName("color3")
    @Expose
    private Object color3;
    @SerializedName("color4")
    @Expose
    private Object color4;
    @SerializedName("color5")
    @Expose
    private Object color5;
    @SerializedName("color6")
    @Expose
    private Object color6;
    @SerializedName("color7")
    @Expose
    private Object color7;
    @SerializedName("color8")
    @Expose
    private Object color8;
    @SerializedName("brief_desc")
    @Expose
    private String briefDesc;
    @SerializedName("current_price")
    @Expose
    private String currentPrice;
    @SerializedName("offer_desc")
    @Expose
    private Object offerDesc;
    @SerializedName("prod_desc")
    @Expose
    private String prodDesc;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("cat_id")
    @Expose
    private Integer catId;
    @SerializedName("sub_cat_id")
    @Expose
    private Integer subCatId;
    @SerializedName("subsub_cat_id")
    @Expose
    private Object subsubCatId;
    @SerializedName("rank")
    @Expose
    private Object rank;
    @SerializedName("offer_price")
    @Expose
    private Object offerPrice;

    public String getProdNum() {
        return prodNum;
    }

    public void setProdNum(String prodNum) {
        this.prodNum = prodNum;
    }

    public String getProdname() {
        return prodname;
    }

    public void setProdname(String prodname) {
        this.prodname = prodname;
    }

    public Object getMetaTitle() {
        return metaTitle;
    }

    public void setMetaTitle(Object metaTitle) {
        this.metaTitle = metaTitle;
    }

    public Object getMetaDesc() {
        return metaDesc;
    }

    public void setMetaDesc(Object metaDesc) {
        this.metaDesc = metaDesc;
    }

    public String getProdImage() {
        return prodImage;
    }

    public void setProdImage(String prodImage) {
        this.prodImage = prodImage;
    }

    public Object getProdLink() {
        return prodLink;
    }

    public void setProdLink(Object prodLink) {
        this.prodLink = prodLink;
    }

    public Object getProdManual() {
        return prodManual;
    }

    public void setProdManual(Object prodManual) {
        this.prodManual = prodManual;
    }

    public Object getOfferText() {
        return offerText;
    }

    public void setOfferText(Object offerText) {
        this.offerText = offerText;
    }

    public String getCatalogueName() {
        return catalogueName;
    }

    public void setCatalogueName(String catalogueName) {
        this.catalogueName = catalogueName;
    }

    public String getCatModelNum() {
        return catModelNum;
    }

    public void setCatModelNum(String catModelNum) {
        this.catModelNum = catModelNum;
    }

    public Object getColor1() {
        return color1;
    }

    public void setColor1(Object color1) {
        this.color1 = color1;
    }

    public Object getColor2() {
        return color2;
    }

    public void setColor2(Object color2) {
        this.color2 = color2;
    }

    public Object getColor3() {
        return color3;
    }

    public void setColor3(Object color3) {
        this.color3 = color3;
    }

    public Object getColor4() {
        return color4;
    }

    public void setColor4(Object color4) {
        this.color4 = color4;
    }

    public Object getColor5() {
        return color5;
    }

    public void setColor5(Object color5) {
        this.color5 = color5;
    }

    public Object getColor6() {
        return color6;
    }

    public void setColor6(Object color6) {
        this.color6 = color6;
    }

    public Object getColor7() {
        return color7;
    }

    public void setColor7(Object color7) {
        this.color7 = color7;
    }

    public Object getColor8() {
        return color8;
    }

    public void setColor8(Object color8) {
        this.color8 = color8;
    }

    public String getBriefDesc() {
        return briefDesc;
    }

    public void setBriefDesc(String briefDesc) {
        this.briefDesc = briefDesc;
    }

    public String getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(String currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Object getOfferDesc() {
        return offerDesc;
    }

    public void setOfferDesc(Object offerDesc) {
        this.offerDesc = offerDesc;
    }

    public String getProdDesc() {
        return prodDesc;
    }

    public void setProdDesc(String prodDesc) {
        this.prodDesc = prodDesc;
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

    public Integer getSubCatId() {
        return subCatId;
    }

    public void setSubCatId(Integer subCatId) {
        this.subCatId = subCatId;
    }

    public Object getSubsubCatId() {
        return subsubCatId;
    }

    public void setSubsubCatId(Object subsubCatId) {
        this.subsubCatId = subsubCatId;
    }

    public Object getRank() {
        return rank;
    }

    public void setRank(Object rank) {
        this.rank = rank;
    }

    public Object getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(Object offerPrice) {
        this.offerPrice = offerPrice;
    }

}
