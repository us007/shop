package com.flipboard.myapplication.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.flipboard.myapplication.R;
import com.flipboard.myapplication.data.db.apihelper.RealmHelper;
import com.flipboard.myapplication.data.db.model.ProductDB;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 */

public class CartProductAdapter extends RecyclerView.Adapter<CartProductAdapter.ViewHolder> {

    private RealmResults<ProductDB> dataSet;
    private Activity mContext;
    private RealmHelper realmHelper;
    private com.flipboard.myapplication.adapter.CartProductInterface cartProductInterface;
    List<String> spinner_data;
    CartProductSpinnerAdapter cartProductSpinnerAdapter;
    Realm mRealm;

    public CartProductAdapter(RealmResults<ProductDB> os_versions, List<String> spinner_data, Activity context, CartProductInterface cartProductInterface) {
        this.dataSet = os_versions;
        this.mContext = context;
        this.cartProductInterface = cartProductInterface;
        realmHelper = new RealmHelper(mContext);
        this.spinner_data = spinner_data;
        mRealm = Realm.getDefaultInstance();
        cartProductSpinnerAdapter = new CartProductSpinnerAdapter(mContext, spinner_data);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemLayoutView = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.list_cart_product, viewGroup, false);
        return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {

        final ProductDB fp = dataSet.get(i);
        String img = fp.getProduct_image();
        String Name = fp.getProduct_name();
        String Product_no = fp.getProduct_no();
        String Product_qty = fp.getProduct_qty();
        String print_location = fp.getProduct_print_location();
        final String printing_location = fp.getProduct_printing_option();

        viewHolder.recyclerViewCartProductEdtPrintingOption.post(new Runnable() {
            @Override
            public void run() {
                if (!printing_location.equals("")){
                    viewHolder.recyclerViewCartProductEdtPrintingOption.setSelection(spinner_data.indexOf(printing_location));
                }
            }
        });

        viewHolder.recyclerViewCartProductNameTxt.setText(Name);
        viewHolder.recyclerViewCartProductNoTxt.setText(Product_no);
        viewHolder.recyclerViewCartProductEdtQty.setText(Product_qty);
        viewHolder.recyclerViewCartProductEdtPrintLocation.setText(print_location);

        viewHolder.recyclerViewCartProductEdtQty.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mRealm.beginTransaction();
                fp.setProduct_qty(s.toString());
                mRealm.commitTransaction();
            }
        });

        viewHolder.recyclerViewCartProductEdtPrintLocation.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mRealm.beginTransaction();
                //ProductDB productDB = mRealm.where(ProductDB.class).equalTo("product_id", fp.getProduct_id()).findFirst();
                fp.setProduct_print_location(s.toString());
                mRealm.commitTransaction();

              /*  realm.executeTransactionAsync(new Realm.Transaction() {
                    public void execute(Realm realm) {
                        MyObjectExtendingRealmObject myObject = new MyObjectExtendingRealmObject("John");
                        realm.insertOrUpdate(myObject); // could be copyToRealmOrUpdate
                    }
                });*/
            }
        });

        viewHolder.recyclerViewCartProductEdtPrintingOption.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mRealm.beginTransaction();
                fp.setProduct_printing_option(adapterView.getItemAtPosition(i).toString());
                mRealm.commitTransaction();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Glide.with(mContext)
                .load(img)
                .placeholder(R.drawable.placeholder)
                .fitCenter()
                .into(viewHolder.recyclerViewCartProductImgImage);

        viewHolder.recyclerViewCartProductDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductDB fp = dataSet.get(i);
                int id = fp.getProduct_id();
                realmHelper.DeleteProduct(id);
                removeAt(i);
                cartProductInterface.Delete();
            }
        });

        viewHolder.recyclerViewCartProductEdtPrintingOption.setAdapter(cartProductSpinnerAdapter);

    }

    public void removeAt(int position) {
        Log.e("remove", "" + position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, dataSet.size());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.recyclerView_cart_product_no_txt)
        TextView recyclerViewCartProductNoTxt;
        @BindView(R.id.recyclerView_cart_product_name_txt)
        TextView recyclerViewCartProductNameTxt;
        @BindView(R.id.recyclerView_cart_product_delete_btn)
        ImageButton recyclerViewCartProductDeleteBtn;
        @BindView(R.id.recyclerView_cart_product_img_image)
        ImageView recyclerViewCartProductImgImage;
        @BindView(R.id.recyclerView_cart_product_edt_qty)
        EditText recyclerViewCartProductEdtQty;
        @BindView(R.id.recyclerView_cart_product_edt_printing_option)
        Spinner recyclerViewCartProductEdtPrintingOption;
        @BindView(R.id.recyclerView_cart_product_edt_print_location)
        EditText recyclerViewCartProductEdtPrintLocation;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            ButterKnife.bind(this, itemLayoutView);
        }

    }

    public RealmResults<ProductDB> productDBList() {
        return dataSet;
    }

}
