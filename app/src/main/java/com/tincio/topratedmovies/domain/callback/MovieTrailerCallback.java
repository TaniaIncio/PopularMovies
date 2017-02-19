package com.tincio.topratedmovies.domain.callback;

import com.tincio.topratedmovies.data.services.response.ResponseReviewsMovie;
import com.tincio.topratedmovies.data.services.response.ResponseTrailersMovie;

/**
 * Created by innovagmd on 10/09/16.
 */
public interface MovieTrailerCallback {

    void onResponse(ResponseTrailersMovie movieTrailer, String... mensajes);
    void onResponseFavorite( String mensajes);
    void onResponseReviews(ResponseReviewsMovie movieReviews, String... mensajes);
}
