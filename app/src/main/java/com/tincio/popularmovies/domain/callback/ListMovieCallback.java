package com.tincio.popularmovies.domain.callback;

import com.tincio.popularmovies.data.services.response.ResponseMovies;

/**
 * Created by innovagmd on 10/09/16.
 */
public interface ListMovieCallback {

    void onResponse(ResponseMovies responseMovies, String...mensajes);
    void onResponseFavorite( String mensajes);
}
