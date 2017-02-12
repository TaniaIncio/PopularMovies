package com.tincio.popularmovies.domain.callback;

import com.tincio.popularmovies.data.services.response.ResponseTrailersMovie;

/**
 * Created by innovagmd on 10/09/16.
 */
public interface MovieTrailerCallback {

    void onResponse(ResponseTrailersMovie movieTrailer, String... mensajes);
    void onResponseFavorite( String mensajes);
}
