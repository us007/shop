package com.silverpixelz.myapplication.ui.pdf;

import com.silverpixelz.myapplication.data.api.apihelper.ApiConfig;
import com.silverpixelz.myapplication.data.api.apihelper.AppConfig;
import com.silverpixelz.myapplication.data.api.model.Pdf.Pdf;
import com.silverpixelz.myapplication.ui.base.Presenter;

import java.net.SocketTimeoutException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by Dharmik Patel on 28-Jun-17.
 */

public class PdfPresenter implements Presenter<PdfActivityView> {

    private PdfActivityView pdfActivityView;

    @Override
    public void onAttach(PdfActivityView view) {
        pdfActivityView = view;
    }

    @Override
    public void onDetach() {
        pdfActivityView = null;
    }

    public void Pdf() {
        pdfActivityView.ShowProgress();
        ApiConfig getResponse = AppConfig.ApiClient().create(ApiConfig.class);
        Call<List<Pdf>> call = getResponse.GetPdf();
        call.enqueue(new Callback<List<Pdf>>() {
            @Override
            public void onResponse(Call<List<Pdf>> call, retrofit2.Response<List<Pdf>> response) {
                List<Pdf> serverResponse = response.body();
                pdfActivityView.HideProgress();
                if (response.isSuccessful()) {
                    pdfActivityView.PdfResponse(serverResponse);
                } else {
                    pdfActivityView.ResponseError("Try Again", "Something went wrong!");
                }
            }

            @Override
            public void onFailure(Call<List<Pdf>> call, Throwable t) {
                if (t instanceof java.net.ConnectException) {
                    pdfActivityView.HideProgress();
                    pdfActivityView.ResponseError("Internet Error", "Please, Check your Internet Connection!");
                } else if (t instanceof SocketTimeoutException) {
                    call.clone().enqueue(this);
                   // pdfActivityView.ResponseError("Timeout Error", "Please, Try again!");
                } else {
                    pdfActivityView.HideProgress();
                    pdfActivityView.ResponseError("Internet Error", "Please, Check your Internet Connection!");
                }
            }
        });
    }

    public void DownloadPdf(String url,String name,int id) {
        pdfActivityView.ShowProgress();
        ServerAPI getResponse = ServerAPI.retrofit.create(ServerAPI.class);
        Call<ResponseBody> call = getResponse.download(url);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                ResponseBody serverResponse = response.body();
                pdfActivityView.HideProgress();
                if (response.isSuccessful()) {
                    pdfActivityView.DownloadResponse(serverResponse,name,id);
                } else {
                    pdfActivityView.DownloadResponseError("Try Again", "Something went wrong!");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                pdfActivityView.HideProgress();
                if (t instanceof java.net.ConnectException) {
                    pdfActivityView.DownloadResponseError("Internet Error", "Please, Check your Internet Connection!");
                } else if (t instanceof SocketTimeoutException) {
                    pdfActivityView.DownloadResponseError("Timeout Error", "Please, Try again!");
                } else {
                    pdfActivityView.DownloadResponseError("Internet Error", "Please, Check your Internet Connection!");
                }
            }
        });
    }

    public interface ServerAPI {
        @GET
        Call<ResponseBody> download(@Url String fileUrl);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://silvergiftz.com/pdf/") // REMEMBER TO END with /
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

}
