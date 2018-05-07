package com.flipboard.myapplication.ui.Products;

import com.flipboard.myapplication.data.api.model.Product.Product;
import com.flipboard.myapplication.data.api.model.SubCategory.SubCategory;
import com.flipboard.myapplication.ui.base.View;

import java.util.List;

/**
 */

public interface ProductView extends View {
    void ShowProgress();

    void HideProgress();

    void ResponseError(String title,String message,int ref);

    void ProductResponse(List<Product> productList);

    void ProductResponseFromSubCategory(List<Product> productList);

    void SubCategoryResponse(List<SubCategory> subCategoryList);
}
