package com.tincio.topratedmovies.presentation.presenter;

import com.tincio.topratedmovies.data.services.response.ResponseReviewsMovie;
import com.tincio.topratedmovies.data.services.response.ResponseTrailersMovie;
import com.tincio.topratedmovies.data.services.response.Result;
import com.tincio.topratedmovies.domain.callback.MovieTrailerCallback;
import com.tincio.topratedmovies.domain.interactor.MovieTrailerInteractor;
import com.tincio.topratedmovies.presentation.view.MovieTrailerView;

public class MovieTrailerPresenter implements MvpPresenter<MovieTrailerView>, MovieTrailerCallback {

    MovieTrailerView view;
    MovieTrailerInteractor movieInteractor;

    @Override
    public void setView(MovieTrailerView view) {
        this.view = view;
        movieInteractor = new MovieTrailerInteractor(this);
    }

    @Override
    public void detachView() {
        view = null;
    }

    @Override
    public void onResponse(ResponseTrailersMovie trailerMovie, String... mensajes) {
        view.showMovieTrailer(trailerMovie, (mensajes.length>0?mensajes[0]:""));
        view.closeLoading();
    }

    @Override
    public void onResponseFavorite(String mensajes) {
        view.showResultFavorite(mensajes);
    }

    @Override
    public void onResponseReviews(ResponseReviewsMovie movieReviews, String... mensajes) {
        view.showMovieReviews(movieReviews, (mensajes.length>0?mensajes[0]:""));
    }

    public void getTrailerByMovie(Integer id){
        view.showLoading();
        movieInteractor.getMovieTrailers(id);
        movieInteractor.getMovieReviews(id);
    }

    public void saveFavoriteMovie(Result result){
        movieInteractor.saveFavorite(result);
    }
}