package com.flipboard.myapplication.ui.home;

import com.flipboard.myapplication.data.api.model.Category.Category;
import com.flipboard.myapplication.ui.base.View;

import java.util.List;

/**
 */

public interface HomeView extends View {

    void ShowProgress();

    void HideProgress();

    void ResponseError(String title,String message);

    void CategoryResponse(List<Category> categoryList);
}
