package com.flipboard.myapplication.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.flipboard.myapplication.R;
import com.flipboard.myapplication.data.api.model.Product.Product;
import com.flipboard.myapplication.data.db.apihelper.RealmHelper;
import com.flipboard.myapplication.events.EventProduct;
import com.flipboard.myapplication.ui.ProductDetail.ProductDetailActivity;
import com.flipboard.myapplication.ui.cart.CartActivity;
import com.flipboard.myapplication.ui.home.HomeActivity;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import static java.util.Comparator.comparing;
/**
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private List<Product> dataSet;
    private Activity mContext;
    private RealmHelper realmHelper;
    private ProductInterface productInterface;

    public ProductAdapter(List<Product> os_versions, Activity context,ProductInterface productInterface) {
        this.dataSet = os_versions;
        this.mContext = context;
        this.productInterface = productInterface;
        realmHelper = new RealmHelper(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemLayoutView = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.list_product, viewGroup, false);
        return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {

        //animate(viewHolder.cardViewProduct,i);
        Product fp = dataSet.get(i);
        String img = "http://www.silvergiftz.com/images/product/"+fp.getProdImage();
        String Name = fp.getProdname();
        String Product_no = fp.getProdNum();

        viewHolder.recyclerViewProductNameTxt.setText(Name);
        viewHolder.recyclerViewProductNoTxt.setText(Product_no);

        /*Picasso.with(mContext) //
                .load(img) //
                //.placeholder(R.drawable.placeholder) //
              //  .error(R.drawable.placeholder) //
                .fit()
                .tag(mContext) //
                .into(viewHolder.recyclerViewProductImg);*/

        Glide.with(mContext)
                .load(img)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .dontTransform()
                .dontAnimate()
                .fitCenter()
                .placeholder(R.drawable.placeholder)
                .into(viewHolder.recyclerViewProductImg);

        viewHolder.recyclerViewProductViewMoreTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Product fp = dataSet.get(i);
                EventBus.getDefault().postSticky(new EventProduct(fp));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(mContext,
                            viewHolder.recyclerViewProductImg, "product_img");
                    Intent intent = new Intent(mContext, ProductDetailActivity.class);
                    mContext.startActivity(intent, options.toBundle());
                }else {
                    Intent i = new Intent(mContext, ProductDetailActivity.class);
                    mContext.startActivity(i);
                }
                /*Intent i = new Intent(mContext, ProductDetailActivity.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mContext.startActivity(i,
                            ActivityOptions.makeSceneTransitionAnimation(mContext).toBundle());
                } else {
                    mContext.startActivity(i);
                }*/
            }
        });
        viewHolder.recyclerViewProductImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Product fp = dataSet.get(i);
                EventBus.getDefault().postSticky(new EventProduct(fp));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(mContext,
                            viewHolder.recyclerViewProductImg, "product_img");
                    Intent intent = new Intent(mContext, ProductDetailActivity.class);
                    mContext.startActivity(intent, options.toBundle());
                }else {
                    Intent i = new Intent(mContext, ProductDetailActivity.class);
                    mContext.startActivity(i);
                }
                /*Intent i = new Intent(mContext, ProductDetailActivity.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mContext.startActivity(i,
                            ActivityOptions.makeSceneTransitionAnimation(mContext).toBundle());
                } else {
                    mContext.startActivity(i);
                }*/
            }
        });
        viewHolder.recyclerViewProductRequestQuoteTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Product fp = dataSet.get(i);
                int id = fp.getId();
                String img = "http://www.silvergiftz.com/images/product/"+fp.getProdImage();
                String Name = fp.getProdname();
                String Product_no = fp.getProdNum();
                Boolean flag = realmHelper.SaveProduct(id,Name,Product_no,img,"","","");
                productInterface.CartResponse(flag);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.recyclerView_product_img)
        ImageView recyclerViewProductImg;
        @BindView(R.id.recyclerView_product_no_txt)
        TextView recyclerViewProductNoTxt;
        @BindView(R.id.recyclerView_product_name_txt)
        TextView recyclerViewProductNameTxt;
        @BindView(R.id.recyclerView_product_view_more_txt)
        TextView recyclerViewProductViewMoreTxt;
        @BindView(R.id.recyclerView_product_request_quote_txt)
        TextView recyclerViewProductRequestQuoteTxt;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            ButterKnife.bind(this, itemLayoutView);
        }

    }


}