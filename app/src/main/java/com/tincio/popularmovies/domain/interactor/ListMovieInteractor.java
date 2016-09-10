package com.tincio.popularmovies.domain.interactor;

import android.provider.SyncStateContract;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.tincio.popularmovies.data.services.response.ResponseMovies;
import com.tincio.popularmovies.domain.callback.ListMovieCallback;
import com.tincio.popularmovies.presentation.application.PopularMoviesApplication;
import com.tincio.popularmovies.presentation.util.Constants;

import org.json.JSONObject;

/**
 * Created by innovagmd on 10/09/16.
 */
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
        PopularMoviesApplication application = PopularMoviesApplication.mApplication;// getApplication(ctx);
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
    }
}
