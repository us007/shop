package com.silverpixelz.myapplication.ui.Products;

import com.silverpixelz.myapplication.data.api.apihelper.ApiConfig;
import com.silverpixelz.myapplication.data.api.apihelper.AppConfig;
import com.silverpixelz.myapplication.data.api.model.Product.Product;
import com.silverpixelz.myapplication.data.api.model.SubCategory.SubCategory;
import com.silverpixelz.myapplication.ui.base.Presenter;

import java.net.SocketTimeoutException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Dharmik Patel on 17-Jun-17.
 */

public class ProductPresenter implements Presenter<ProductView> {

    private ProductView productView;

    @Override
    public void onAttach(ProductView view) {
        productView = view;
    }

    @Override
    public void onDetach() {
        productView = null;
    }

    public void SubCategory(int id){
        productView.ShowProgress();
        ApiConfig getResponse = AppConfig.ApiClient().create(ApiConfig.class);
        Call<List<SubCategory>> call = getResponse.GetSubCategory(id);
        call.enqueue(new Callback<List<SubCategory>>() {
            @Override
            public void onResponse(Call<List<SubCategory>> call, retrofit2.Response<List<SubCategory>> response) {
                List<SubCategory> serverResponse = response.body();
                //productView.HideProgress();
                if (response.isSuccessful()) {
                    productView.SubCategoryResponse(serverResponse);
                } else {
                    productView.ResponseError("Try Again","Something went wrong!",1);
                }
            }

            @Override
            public void onFailure(Call<List<SubCategory>> call, Throwable t) {
                if (t instanceof java.net.ConnectException) {
                    productView.HideProgress();
                    productView.ResponseError("Internet Error", "Please, Check your Internet Connection!",1);
                } else if (t instanceof SocketTimeoutException) {
                   // productView.HideProgress();
                    call.clone().enqueue(this);
                   // productView.ResponseError("Timeout Error", "Please, Try again!",1);
                } else {
                    productView.HideProgress();
                    productView.ResponseError("Internet Error", "Please, Check your Internet Connection!",1);
                }
            }
        });
    }

    public void Product(int id){
       // productView.ShowProgress();
        ApiConfig getResponse = AppConfig.ApiClient().create(ApiConfig.class);
        Call<List<Product>> call = getResponse.GetProducts(id);
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, retrofit2.Response<List<Product>> response) {
                List<Product> serverResponse = response.body();
                productView.HideProgress();
                if (response.isSuccessful()) {
                    productView.ProductResponse(serverResponse);
                } else {
                    productView.ResponseError("Try Again","Something went wrong!",2);
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                if (t instanceof java.net.ConnectException) {
                    productView.HideProgress();
                    productView.ResponseError("Internet Error", "Please, Check your Internet Connection!",2);
                } else if (t instanceof SocketTimeoutException) {
                    call.clone().enqueue(this);
                   // productView.ResponseError("Timeout Error", "Please, Try again!",2);
                } else {
                    productView.HideProgress();
                    productView.ResponseError("Internet Error", "Please, Check your Internet Connection!",2);
                }
            }
        });
    }

    public void ProductSubCategory(int id){
        productView.ShowProgress();
        ApiConfig getResponse = AppConfig.ApiClient().create(ApiConfig.class);
        Call<List<Product>> call = getResponse.GetProductsFromSubCategory(id);
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, retrofit2.Response<List<Product>> response) {
                List<Product> serverResponse = response.body();
                productView.HideProgress();
                if (response.isSuccessful()) {
                    productView.ProductResponse(serverResponse);
                } else {
                    productView.ResponseError("Try Again","Something went wrong!",3);
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                if (t instanceof java.net.ConnectException) {
                    productView.HideProgress();
                    productView.ResponseError("Internet Error", "Please, Check your Internet Connection!",3);
                } else if (t instanceof SocketTimeoutException) {
                    call.clone().enqueue(this);
                    //productView.ResponseError("Timeout Error", "Please, Try again!",3);
                } else {
                    productView.HideProgress();
                    productView.ResponseError("Internet Error", "Please, Check your Internet Connection!",3);
                }
            }
        });
    }
}
