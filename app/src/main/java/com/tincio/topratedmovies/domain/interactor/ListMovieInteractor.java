package com.tincio.topratedmovies.domain.interactor;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.tincio.topratedmovies.R;
import com.tincio.topratedmovies.data.model.FavoriteDataBase;
import com.tincio.topratedmovies.data.model.PopularMoviesContentProvider;
import com.tincio.topratedmovies.data.services.response.ResponseMovies;
import com.tincio.topratedmovies.data.services.response.Result;
import com.tincio.topratedmovies.domain.callback.ListMovieCallback;
import com.tincio.topratedmovies.presentation.application.PopularMoviesApplication;
import com.tincio.topratedmovies.presentation.util.Constants;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;


public class ListMovieInteractor{

    ListMovieCallback callback;
    public int TIMEOUT = 5000;
    PopularMoviesApplication application = PopularMoviesApplication.mApplication;
    public ListMovieInteractor(ListMovieCallback callback){
        this.callback = callback;
    }

    public void callListMovies(String option){
        try{
            if(option.equals(application.getResources().getString(R.string.id_order_three))){
                getFavoriteContentProvider();
            }else{
                getRequesListMovies(Constants.serviceNames.GET_LIST_MOVIES(option));
            }
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
                                callback.onResponse(checkFavoriteInList(responseMovies));
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

     ResponseMovies checkFavoriteInList(ResponseMovies responseMovies){

        Realm realm = application.getRealm();
        List<Result> lista = responseMovies.getResults();
         List<Result> listFav = parseCursorToList();
        int indice = 0;
        try{
            for(Result result: lista){
                result.setFavorito(false);
                for (Result resultFav : listFav){
                    if(result.getId().equals(resultFav.getId())){
                        result.setFavorito(true);
                        break;
                    }

                }
                lista.set(indice, result);
                indice = indice+1;
            }
          responseMovies.setResults(lista);
        }catch(Exception e){
            e.printStackTrace();
        }
         return responseMovies;
    }

    //for favorite

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

    public void getFavoriteContentProvider(){
        ///cursor
        List<Result> list = parseCursorToList();
        ResponseMovies responseMovies = new ResponseMovies();
        responseMovies.setResults(list);
        callback.onResponse(checkFavoriteInList(responseMovies));

    }

    public List<Result> parseCursorToList(){
        Cursor data = application.getContentResolver().query(PopularMoviesContentProvider.CONTENT_URI, PopularMoviesContentProvider.FAVORITE_COLUMNS, null,
                PopularMoviesContentProvider.FAVORITE_COLUMNS, PopularMoviesContentProvider.Favorite.COL_NOMBRE);

        List<Result> list = new ArrayList<>();
        Result mResult;
        if (data != null) {
            while(data.moveToNext()) {
                mResult = new Result();
                mResult.setFavorito(true);
                mResult.setId(data.getInt(1));
                mResult.setTitle(data.getString(2));
                mResult.setPosterPath(data.getString(3));
                mResult.setOverview(data.getString(4));
                mResult.setReleaseDate(data.getString(5));
                mResult.setBackdropPath(data.getString(8));

                list.add(mResult);
            }
            data.close();
        }
        return list;
    }


}
