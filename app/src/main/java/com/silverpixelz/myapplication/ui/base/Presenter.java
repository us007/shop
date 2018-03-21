package com.silverpixelz.myapplication.ui.base;

/**
 * Created by Dharmik Patel on 16-Jun-17.
 */

public interface Presenter<T extends View> {
    void onAttach(T view);

    void onDetach();
}
