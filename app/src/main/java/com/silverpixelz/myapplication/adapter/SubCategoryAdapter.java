package com.silverpixelz.myapplication.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.silverpixelz.myapplication.R;
import com.silverpixelz.myapplication.data.api.model.SubCategory.SubCategory;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dharmik Patel on 17-Jun-17.
 */

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.ViewHolder> {

    private List<SubCategory> dataSet;
    private Activity mContext;
    private SubCategoryInterface subCategoryInterface;

    public SubCategoryAdapter(List<SubCategory> os_versions, Activity context,SubCategoryInterface subCategoryInterface) {
        this.dataSet = os_versions;
        this.mContext = context;
        this.subCategoryInterface = subCategoryInterface;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemLayoutView = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.list_subcategory, viewGroup, false);
        return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {

        //animate(viewHolder.cardViewProduct,i);
        SubCategory fp = dataSet.get(i);
        String Name = fp.getSubcatname();
        int count = fp.getCount();

        viewHolder.recyclerViewSubCategoryTxt.setText(Name+" ("+count+")");
        viewHolder.recyclerViewSubCategoryLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubCategory fp = dataSet.get(i);
                int category = fp.getId();
                subCategoryInterface.ProductFromSubCategory(category);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.recyclerView_sub_category_txt)
        TextView recyclerViewSubCategoryTxt;
        @BindView(R.id.recyclerView_sub_category_layout)
        RelativeLayout recyclerViewSubCategoryLayout;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            ButterKnife.bind(this, itemLayoutView);
        }

    }
}
