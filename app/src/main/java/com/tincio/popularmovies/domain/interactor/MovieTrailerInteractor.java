package com.tincio.popularmovies.domain.interactor;

import android.content.ContentValues;
import android.net.Uri;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.tincio.popularmovies.R;
import com.tincio.popularmovies.data.model.FavoriteDataBase;
import com.tincio.popularmovies.data.model.PopularMoviesContentProvider;
import com.tincio.popularmovies.data.services.response.ResponseReviewsMovie;
import com.tincio.popularmovies.data.services.response.ResponseTrailersMovie;
import com.tincio.popularmovies.data.services.response.Result;
import com.tincio.popularmovies.domain.callback.MovieTrailerCallback;
import com.tincio.popularmovies.presentation.application.PopularMoviesApplication;
import com.tincio.popularmovies.presentation.util.Constants;

import org.json.JSONObject;

public class MovieTrailerInteractor {

    MovieTrailerCallback callback;
    public int TIMEOUT = 5000;
    PopularMoviesApplication application = PopularMoviesApplication.mApplication;
    public MovieTrailerInteractor(MovieTrailerCallback callback){
        this.callback = callback;
    }

    public void getMovieTrailers(Integer id){
        try{
            Log.i("tag movie", Constants.serviceNames.GET_TRAILERS(id));
            getRequesListMovies(Constants.serviceNames.GET_TRAILERS(id));
        }catch(Exception e){
            throw e;
        }
    }

    void getRequesListMovies(String url) {
        try{

            if (application != null) {
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                        url,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Gson gson = new Gson();
                                ResponseTrailersMovie responseMovies = gson.fromJson(response.toString(), ResponseTrailersMovie.class);
                                callback.onResponse(responseMovies);

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                callback.onResponse(null, error.getMessage());
                            }
                        });
                jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                        TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                application.getRequestQueue().add(jsonObjectRequest);
            }
        }catch(Exception e){
            throw e;
        }
    }

    //For favorite
    public void saveFavorite(Result mResult){
        try{
            Uri mNewUri;
            if(mResult.getFavorito() == false){
                ContentValues mNewValues = new ContentValues();
                mNewValues.put(PopularMoviesContentProvider.Favorite.ID_MOVIE, mResult.getId());
                mNewValues.put(PopularMoviesContentProvider.Favorite.COL_NOMBRE, mResult.getTitle());
                mNewValues.put(PopularMoviesContentProvider.Favorite.PATH, mResult.getPosterPath());
                mNewValues.put(PopularMoviesContentProvider.Favorite.BACKDROP_PATH, mResult.getBackdropPath());
                mNewValues.put(PopularMoviesContentProvider.Favorite.OVERVIEW, mResult.getOverview());
                mNewValues.put(PopularMoviesContentProvider.Favorite.RELEASEDATE, mResult.getReleaseDate());
                mNewValues.put(PopularMoviesContentProvider.Favorite.VOTE_AVERAGE, mResult.getVoteAverage());

                mNewUri = application.getContentResolver().insert(
                        PopularMoviesContentProvider.CONTENT_URI,   // the user dictionary content URI
                        mNewValues                          // the values to insert
                );
            }else{
                FavoriteDataBase dataBase = new FavoriteDataBase(application);
                dataBase.deleteFavorite(mResult.getId());
            }
            callback.onResponseFavorite(application.getString(R.string.response_succesfull));
        }catch(Exception e){
            callback.onResponseFavorite(application.getString(R.string.response_error)+e.getMessage());
            //  throw e;
        }
    }

    //get reviews of usuers
    public void getMovieReviews(Integer id){
        try{
            getRequestReviewsMovies(Constants.serviceNames.GET_REVIEWS(id));
        }catch(Exception e){
            throw e;
        }
    }
    void getRequestReviewsMovies(String url) {
        try{

            if (application != null) {
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                        url,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Gson gson = new Gson();
                                ResponseReviewsMovie responseMovies = gson.fromJson(response.toString(), ResponseReviewsMovie.class);
                                callback.onResponseReviews(responseMovies);

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                callback.onResponse(null, error.getMessage());
                            }
                        });
                jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                        TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                application.getRequestQueue().add(jsonObjectRequest);
            }
        }catch(Exception e){
            throw e;
        }
    }

}
