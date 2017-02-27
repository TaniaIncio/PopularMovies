package com.tincio.topratedmovies.presentation.util;

import android.net.Uri;
import android.util.Log;

/**
 * Created by innovagmd on 10/09/16.
 */
public final class  Constants {

    public static String KEY = "39335edf6af0e5ee10c4be3cded34eb1";
    public static String param = "api_key";
    public static String param_page = "page";
    public static final String TABLE_FAVORITE = "Favorite";
    public static class serviceNames{
        public static String BASE_MOVIES = "http://api.themoviedb.org/3/movie/";
        public static String GET_IMAGE_MOVIES = "http://image.tmdb.org/t/p/w780";
        public static String GET_TRAILERS(Integer id){
            StringBuilder builderBase = new StringBuilder();
            builderBase.append(BASE_MOVIES);
            builderBase.append(id);
            builderBase.append("/videos?");
            Uri builUri = Uri.parse(builderBase.toString()).buildUpon()
                    .appendQueryParameter(param,KEY).build();
            return builUri.toString();
        }

        public static String GET_LIST_MOVIES(String option, int page){
            StringBuilder builderBase = new StringBuilder();
            builderBase.append(BASE_MOVIES);
            builderBase.append(option);
            builderBase.append("?");
            Uri builUri = Uri.parse(builderBase.toString()).buildUpon()
                    .appendQueryParameter(param,KEY)
                    .appendQueryParameter(param_page,String.valueOf(page)).build();
            Log.i("taag",builUri.toString());
            return builUri.toString();
        }

        public static String GET_REVIEWS(Integer id){
            StringBuilder builderBase = new StringBuilder();
            builderBase.append(BASE_MOVIES);
            builderBase.append(id);
            builderBase.append("/reviews?");
            Uri builUri = Uri.parse(builderBase.toString()).buildUpon()
                    .appendQueryParameter(param,KEY).build();
            return builUri.toString();
        }
    }
}