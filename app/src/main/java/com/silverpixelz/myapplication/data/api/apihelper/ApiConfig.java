package com.silverpixelz.myapplication.data.api.apihelper;

import com.silverpixelz.myapplication.data.api.model.CartRequest;
import com.silverpixelz.myapplication.data.api.model.Category.Category;
import com.silverpixelz.myapplication.data.api.model.Country.Country;
import com.silverpixelz.myapplication.data.api.model.Pdf.Pdf;
import com.silverpixelz.myapplication.data.api.model.Product.Product;
import com.silverpixelz.myapplication.data.api.model.Quote.QuoteResponse;
import com.silverpixelz.myapplication.data.api.model.SubCategory.SubCategory;
import com.silverpixelz.myapplication.data.api.model.SubscribeRequest;
import com.silverpixelz.myapplication.data.api.model.printOption.PrintOption;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Dharmik Patel on 17-Jun-17.
 */

public interface ApiConfig {

    // Category
    @GET("categories")
    Call<List<Category>> GetCategory();

    // SubCategory
    @GET("categories/{id}/subcategories")
    Call<List<SubCategory>> GetSubCategory(@Path("id") int id);

    // Product
    @GET("categories/{id}/products")
    Call<List<Product>> GetProducts(@Path("id") int id);

    // Product From SubCategory
    @GET("subcategories/{id}/products")
    Call<List<Product>> GetProductsFromSubCategory(@Path("id") int id);

    // Product by id
    @GET("products/{id}")
    Call<Product> ProductById(@Path("id") String id);

    // Product From SubCategory
    @GET("products/search")
    Call<List<Product>> Search(@Query("search") String first);

    // PDF
    @GET("pdf_repositories")
    Call<List<Pdf>> GetPdf();

    // SubScribe
    @POST("subscribes")
    Call<String> Subscribe(@Body SubscribeRequest body);

    // Quote
    @POST("PlaceOrderNew")
    Call<QuoteResponse> Quote(@Body CartRequest body);

    // Country
    @GET("country")
    Call<List<Country>> GetCountry();

    // PrintOption
    @GET("printOption")
    Call<List<PrintOption>> GetPrintOption();

    /*// SubScribe
    @POST("subscribes")
    Call<String> Subscribe(@Body SubscribeRequest body);*/
}
