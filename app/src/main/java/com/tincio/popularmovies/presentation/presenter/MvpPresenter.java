package com.tincio.popularmovies.presentation.presenter;

/**
 * Created by tincio on 25/05/2016.
 */
public interface MvpPresenter<V> {
    public void setView(V view);
    public void detachView();
}
