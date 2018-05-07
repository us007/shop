
package com.flipboard.myapplication.data.api.model;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class CartResponse {

    @SerializedName("table")
    private List<Table> mTable;

    public List<Table> getTable() {
        return mTable;
    }

    public void setTable(List<Table> table) {
        mTable = table;
    }

}
