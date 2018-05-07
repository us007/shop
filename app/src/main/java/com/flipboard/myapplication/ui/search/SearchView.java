package com.flipboard.myapplication.ui.search;

import com.flipboard.myapplication.data.api.model.Product.Product;
import com.flipboard.myapplication.ui.base.View;

import java.util.List;

/**
 */

public interface SearchView extends View {

    void ShowProgress();

    void HideProgress();

    void ResponseError(String title,String message);

    void ProductResponse(List<Product> productList);
}
