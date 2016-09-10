package com.tincio.popularmovies.presentation.presenter;

import com.tincio.popularmovies.data.services.response.ResponseMovies;
import com.tincio.popularmovies.domain.callback.ListMovieCallback;
import com.tincio.popularmovies.domain.interactor.ListMovieInteractor;
import com.tincio.popularmovies.presentation.view.ListMovieView;

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

    public void callListMovie(){
        try{
       //     view.showLoading();
            movieInteractor.callListMovies();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onResponse(ResponseMovies responseMovies, String... mensajes) {
   //     view.closeLoading();
        view.showListMovies(responseMovies, (mensajes.length>0?mensajes[0]:""));
    }
}