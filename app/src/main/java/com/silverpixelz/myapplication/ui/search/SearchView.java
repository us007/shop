package com.silverpixelz.myapplication.ui.search;

import com.silverpixelz.myapplication.data.api.model.Product.Product;
import com.silverpixelz.myapplication.ui.base.View;

import java.util.List;

/**
 * Created by Dharmik Patel on 23-Jun-17.
 */

public interface SearchView extends View {

    void ShowProgress();

    void HideProgress();

    void ResponseError(String title,String message);

    void ProductResponse(List<Product> productList);
}
