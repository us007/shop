package com.flipboard.myapplication.ui.base;

/**
 */

public interface Presenter<T extends View> {
    void onAttach(T view);

    void onDetach();
}
