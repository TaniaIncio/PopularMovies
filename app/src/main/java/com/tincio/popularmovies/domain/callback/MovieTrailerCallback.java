package com.tincio.popularmovies.domain.callback;

import com.tincio.popularmovies.data.model.MovieRealm;
import com.tincio.popularmovies.data.services.response.ResponseMovies;
import com.tincio.popularmovies.data.services.response.ResponseTrailersMovie;
import com.tincio.popularmovies.data.services.response.ResultTrailer;

/**
 * Created by innovagmd on 10/09/16.
 */
public interface MovieTrailerCallback {

    void onResponse(ResponseTrailersMovie movieTrailer, String... mensajes);
    void onResponseFavorite( String mensajes);
}
