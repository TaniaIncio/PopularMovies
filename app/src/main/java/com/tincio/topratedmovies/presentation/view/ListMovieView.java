package com.tincio.topratedmovies.presentation.view;

import com.tincio.topratedmovies.data.services.response.ResponseMovies;

/**
 * Created by tincio on 10/09/16.
 */
public interface ListMovieView extends MvpView {
    void showListMovies(ResponseMovies responseMovies, String responseError);
    void showResultFavorite(String response);
}
