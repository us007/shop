package com.silverpixelz.myapplication.ui.search;

import com.silverpixelz.myapplication.data.api.apihelper.ApiConfig;
import com.silverpixelz.myapplication.data.api.apihelper.AppConfig;
import com.silverpixelz.myapplication.data.api.model.Product.Product;
import com.silverpixelz.myapplication.ui.base.Presenter;

import java.net.SocketTimeoutException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Dharmik Patel on 23-Jun-17.
 */

public class SearchPresenter implements Presenter<SearchView> {

    private SearchView searchView;

    @Override
    public void onAttach(SearchView view) {
        searchView = view;
    }

    @Override
    public void onDetach() {
        searchView = null;
    }

    public void Product(String String) {
        if (searchView != null) {
            searchView.ShowProgress();
        }
        ApiConfig getResponse = AppConfig.ApiClient().create(ApiConfig.class);
        Call<List<Product>> call = getResponse.Search(String);
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, retrofit2.Response<List<Product>> response) {
                List<Product> serverResponse = response.body();
                if (searchView != null) {
                    searchView.HideProgress();
                    if (response.isSuccessful()) {
                        searchView.ProductResponse(serverResponse);
                    } else {
                        searchView.ResponseError("Try Again", "Something went wrong!");
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                if (searchView != null) {
                    if (t instanceof java.net.ConnectException) {
                        searchView.HideProgress();
                        searchView.ResponseError("Internet Error", "Please, Check your Internet Connection!");
                    } else if (t instanceof SocketTimeoutException) {
                        call.clone().enqueue(this);
                       // searchView.ResponseError("Timeout Error", "Please, Try again!");
                    } else {
                        searchView.HideProgress();
                        searchView.ResponseError("Internet Error", "Please, Check your Internet Connection!");
                    }
                }
            }
        });
    }
}
