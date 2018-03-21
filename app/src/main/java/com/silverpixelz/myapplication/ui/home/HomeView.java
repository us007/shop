package com.silverpixelz.myapplication.ui.home;

import com.silverpixelz.myapplication.data.api.model.Category.Category;
import com.silverpixelz.myapplication.ui.base.View;

import java.util.List;

/**
 * Created by Dharmik Patel on 16-Jun-17.
 */

public interface HomeView extends View {

    void ShowProgress();

    void HideProgress();

    void ResponseError(String title,String message);

    void CategoryResponse(List<Category> categoryList);
}
