package com.tincio.popularmovies.presentation.util;

/**
 * Created by innovagmd on 10/09/16.
 */
public class Constants {

    private static String KEY = "39335edf6af0e5ee10c4be3cded34eb1";
    public static class serviceNames{
        public static String GET_LIST_MOVIES = "https://api.themoviedb.org/3/movie/popular?api_key="+KEY;
        public static String GET_IMAGE_MOVIES = "http://image.tmdb.org/t/p/w185";
        //http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg
    }
}