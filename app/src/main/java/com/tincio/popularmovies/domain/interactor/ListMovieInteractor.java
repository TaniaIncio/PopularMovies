package com.tincio.popularmovies.domain.interactor;

import android.provider.SyncStateContract;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.internal.bind.ArrayTypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.tincio.popularmovies.data.model.MovieRealm;
import com.tincio.popularmovies.data.services.response.ResponseMovies;
import com.tincio.popularmovies.domain.callback.ListMovieCallback;
import com.tincio.popularmovies.presentation.application.PopularMoviesApplication;
import com.tincio.popularmovies.presentation.util.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;


public class ListMovieInteractor {

    ListMovieCallback callback;
    public int TIMEOUT = 5000;
    public ListMovieInteractor(ListMovieCallback callback){
        this.callback = callback;
    }

    public void callListMovies(){
        try{
            getRequesListMovies(Constants.serviceNames.GET_LIST_MOVIES);
        }catch(Exception e){
            throw e;
        }
    }

    void getRequesListMovies(String url) {
        try{
            PopularMoviesApplication application = PopularMoviesApplication.mApplication;
            if (application != null) {
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                        url,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Gson gson = new Gson();
                                ResponseMovies responseMovies = gson.fromJson(response.toString(), ResponseMovies.class);
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

    //for favorite

    public void saveFavorite(Integer id){
        try{
            PopularMoviesApplication application = PopularMoviesApplication.mApplication;
            Realm realm = application.getRealm();
            realm.beginTransaction();
            MovieRealm movieSelection = realm.where(MovieRealm.class).equalTo("id",id).findFirst();
            if(movieSelection!=null){
                movieSelection.setFavorite(!movieSelection.getFavorite());
            }else{
                MovieRealm movieRealm = new MovieRealm();
                movieRealm.setFavorite(true);
                movieRealm.setId(id);
                realm.copyToRealm(movieRealm);
            }
            realm.commitTransaction();
            callback.onResponseFavorite("succesfull");
        }catch(Exception e){
            callback.onResponseFavorite("error");
            //  throw e;
        }
    }

}
