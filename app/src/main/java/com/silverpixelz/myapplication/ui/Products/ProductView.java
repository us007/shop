package com.silverpixelz.myapplication.ui.Products;

import com.silverpixelz.myapplication.data.api.model.Product.Product;
import com.silverpixelz.myapplication.data.api.model.SubCategory.SubCategory;
import com.silverpixelz.myapplication.ui.base.View;

import java.util.List;

/**
 * Created by Dharmik Patel on 17-Jun-17.
 */

public interface ProductView extends View {
    void ShowProgress();

    void HideProgress();

    void ResponseError(String title,String message,int ref);

    void ProductResponse(List<Product> productList);

    void ProductResponseFromSubCategory(List<Product> productList);

    void SubCategoryResponse(List<SubCategory> subCategoryList);
}
