package com.tincio.popularmovies.presentation.view;

import android.content.Context;

/**
 * Created by tincio on 10/09/16.
 */
public interface MvpView {
    Context getContext();
    void showLoading();
    void closeLoading();

}
