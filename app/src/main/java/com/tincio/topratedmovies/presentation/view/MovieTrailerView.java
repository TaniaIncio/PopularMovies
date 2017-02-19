package com.tincio.topratedmovies.presentation.view;

import com.tincio.topratedmovies.data.services.response.ResponseReviewsMovie;
import com.tincio.topratedmovies.data.services.response.ResponseTrailersMovie;

/**
 * Created by tincio on 10/09/16.
 */
public interface MovieTrailerView extends MvpView {
    void showMovieTrailer(ResponseTrailersMovie detailMovie, String responseError);
    void showResultFavorite(String response);
    void showMovieReviews(ResponseReviewsMovie detailMovie, String responseError);
}
