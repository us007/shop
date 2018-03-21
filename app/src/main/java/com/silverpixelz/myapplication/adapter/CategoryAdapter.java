package com.silverpixelz.myapplication.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.silverpixelz.myapplication.R;
import com.silverpixelz.myapplication.data.api.model.Category.Category;
import com.silverpixelz.myapplication.helper.Config;
import com.silverpixelz.myapplication.ui.ProductDetail.ProductDetailActivity;
import com.silverpixelz.myapplication.ui.Products.ProductActivity;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dharmik Patel on 17-Jun-17.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private List<Category> dataSet;
    private Activity mContext;

    public CategoryAdapter(List<Category> os_versions, Activity context) {
        this.dataSet = os_versions;
        this.mContext = context;
        Collections.sort(dataSet, (o1, o2) -> o1.getRank().compareTo(o2.getRank()));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemLayoutView = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.list_category, viewGroup, false);
        return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {

        //animate(viewHolder.cardViewProduct,i);
        Category fp = dataSet.get(i);
        final String img = "http://www.silvergiftz.com/images/category/"+fp.getImage();
        String Name = fp.getName();

        viewHolder.recyclerViewCategoryTxt.setText(Name);
        Glide.with(mContext)
                .load(img)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .dontTransform()
                .placeholder(R.drawable.placeholder)
                .into(viewHolder.recyclerViewCategoryImg);

        viewHolder.recyclerViewCategoryLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Category fp = dataSet.get(i);
                int category = fp.getId();
                Intent i = new Intent(mContext, ProductActivity.class);
                i.putExtra(Config.CATEGORY_ID,category);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mContext.startActivity(i,
                            ActivityOptions.makeSceneTransitionAnimation(mContext).toBundle());
                } else {
                    mContext.startActivity(i);
                }
                /*Intent i = new Intent(mContext, ProductActivity.class);
                i.putExtra(Config.CATEGORY_ID,category);
                mContext.startActivity(i);*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.recyclerView_category_img)
        ImageView recyclerViewCategoryImg;
        @BindView(R.id.recyclerView_category_txt)
        TextView recyclerViewCategoryTxt;
        @BindView(R.id.recyclerView_category_layout)
        RelativeLayout recyclerViewCategoryLayout;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            ButterKnife.bind(this, itemLayoutView);
        }

    }
}
