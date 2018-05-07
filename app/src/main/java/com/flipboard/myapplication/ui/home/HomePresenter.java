package com.flipboard.myapplication.ui.home;

import com.flipboard.myapplication.data.api.apihelper.ApiConfig;
import com.flipboard.myapplication.data.api.apihelper.AppConfig;
import com.flipboard.myapplication.data.api.model.Category.Category;
import com.flipboard.myapplication.ui.base.Presenter;

import java.net.SocketTimeoutException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

/**
 */

public class HomePresenter implements Presenter<HomeView> {

    private HomeView homeView;

    @Override
    public void onAttach(HomeView view) {
        homeView = view;
    }

    @Override
    public void onDetach() {
        homeView = null;
    }

    public void Category(){
        homeView.ShowProgress();
        ApiConfig getResponse = AppConfig.ApiClient().create(ApiConfig.class);
        Call<List<Category>> call = getResponse.GetCategory();
        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, retrofit2.Response<List<Category>> response) {
                List<Category> serverResponse = response.body();
                homeView.HideProgress();
                if (response.isSuccessful()) {
                    homeView.CategoryResponse(serverResponse);
                } else {
                    homeView.ResponseError("Try Again","Something went wrong!");
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                if (t instanceof java.net.ConnectException) {
                    homeView.HideProgress();
                    homeView.ResponseError("Internet Error", "Please, Check your Internet Connection!");
                } else if (t instanceof SocketTimeoutException) {
                    call.clone().enqueue(this);
                    // homeView.ResponseError("Timeout Error", "Please, Try again!");
                } else {
                    homeView.HideProgress();
                    //call.clone();
                    homeView.ResponseError("Internet Error", "Please, Check your Internet Connection!");
                }
            }
        });
    }
}
