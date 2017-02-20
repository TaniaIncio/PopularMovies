package com.tincio.topratedmovies.presentation.presenter;

import android.util.Log;

import com.tincio.topratedmovies.data.services.response.ResponseMovies;
import com.tincio.topratedmovies.data.services.response.Result;
import com.tincio.topratedmovies.domain.callback.ListMovieCallback;
import com.tincio.topratedmovies.domain.interactor.ListMovieInteractor;
import com.tincio.topratedmovies.presentation.view.ListMovieView;

/**
 * Created by innovagmd on 10/09/16.
 */
public class ListMoviePresenter implements MvpPresenter<ListMovieView>, ListMovieCallback {

    ListMovieView view;
    ListMovieInteractor movieInteractor;

    @Override
    public void setView(ListMovieView view) {
        this.view = view;
        movieInteractor = new ListMovieInteractor(this);
    }

    @Override
    public void detachView() {
        view = null;
    }

    public void callListMovie(String option, int page){
        try{
            Log.i("TAG", "callistmovie");
            if(page == 1)
                view.showLoading();
            movieInteractor.callListMovies(option, page);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onResponse(ResponseMovies responseMovies, String... mensajes) {
        view.closeLoading();
        view.showListMovies(responseMovies, (mensajes.length>0?mensajes[0]:""));
    }

    public void saveFavoriteMovie(Result mResult){
        movieInteractor.saveFavorite(mResult);
    }

    @Override
    public void onResponseFavorite(String mensajes) {
        view.showResultFavorite(mensajes);
    }


}
