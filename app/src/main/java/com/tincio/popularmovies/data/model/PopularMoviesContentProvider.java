package com.tincio.popularmovies.data.model;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;

import com.tincio.popularmovies.presentation.util.Constants;

/**
 * Created by tincio on 08/02/17.
 */

    public class PopularMoviesContentProvider extends ContentProvider {
    private static final String uri =
            "content://com.tincio.popularmovies.contentproviders/Favorite";

    public static final Uri CONTENT_URI = Uri.parse(uri);

    /**Para poder acceder a la tabla y/o valores de la tabla*/
    //UriMatcher
            //acceso generico a la tabla
    private static final int FAVORITE = 1;
    //acceso a una determinada fila
    private static final int FAVORITE_ID = 2;
    private static  UriMatcher uriMatcher = null;

    /**Variables para acceder a la base de datos*/
    //Base de datos
    private FavoriteDataBase favoriteDataBase;
    private static final String BD_NOMBRE = "DBFavorite";
    private static final int BD_VERSION = 1;
    public static final String[] FAVORITE_COLUMNS = {
            Favorite.ID_MOVIE,
            Favorite.COL_NOMBRE,
            Favorite.PATH,
            Favorite.OVERVIEW,
            Favorite.RELEASEDATE,
            Favorite.ORIGINAL_TITLE,
            Favorite.ORIGINAL_LANGUAGE,
            Favorite.BACKDROP_PATH,
            Favorite.VOTE_AVERAGE
    };

    @Override
    public boolean onCreate() {
        favoriteDataBase = new FavoriteDataBase(
                getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

            //Si es una consulta a un ID concreto construimos el WHERE
            String where = selection;
            if(uriMatcher.match(uri) == FAVORITE_ID){
                where = "_id=" + uri.getLastPathSegment();
            }

            SQLiteDatabase db = favoriteDataBase.getWritableDatabase();

            Cursor c = db.query( Constants.TABLE_FAVORITE, null, null,
                    null, null, null, null);

            return c;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        int match = uriMatcher.match(uri);

        switch (match)
        {
            case FAVORITE:
                return "vnd.android.cursor.dir/vnd.tincio.favorite";
            case FAVORITE_ID:
                return "vnd.android.cursor.item/vnd.tincio.favorite";
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long regId = 1;

        SQLiteDatabase db = favoriteDataBase.getWritableDatabase();

        regId = db.insert( Constants.TABLE_FAVORITE, null, values);

        Uri newUri = ContentUris.withAppendedId(CONTENT_URI, regId);

        return newUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int cont;

        //Si es una consulta a un ID concreto construimos el WHERE
        String where = selection;
        if(uriMatcher.match(uri) == FAVORITE_ID){
            where = "_id=" + uri.getLastPathSegment();
        }

        SQLiteDatabase db = favoriteDataBase.getWritableDatabase();

        cont = db.delete( Constants.TABLE_FAVORITE, where, selectionArgs);

        return cont;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int cont;

        //Si es una consulta a un ID concreto construimos el WHERE
        String where = selection;
        if(uriMatcher.match(uri) == FAVORITE_ID){
            where = "_id=" + uri.getLastPathSegment();
        }

        SQLiteDatabase db = favoriteDataBase.getWritableDatabase();

        cont = db.update( Constants.TABLE_FAVORITE, values, where, selectionArgs);

        return cont;
    }

    public static final class Favorite implements BaseColumns
    {
        private Favorite() {}

        public static final String COL_NOMBRE = "nombre";
        public static final String ID_MOVIE = "id_movie";
        public static final String PATH = "path";
        public static final String OVERVIEW = "overview";
        public static final String RELEASEDATE = "releaseDate";
        public static final String ORIGINAL_TITLE = "originalTitle";
        public static final String ORIGINAL_LANGUAGE = "originalLanguage";
        public static final String BACKDROP_PATH = "backdropPath";
        public static final String VOTE_AVERAGE = "voteAverage";
    }

    /**Urimatcher*/
    //Inicializamos el UriMatcher
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI("com.tincio.popularmovies.contentproviders", "favorite", FAVORITE);
        uriMatcher.addURI("com.tincio.popularmovies.contentproviders", "favorite/#", FAVORITE_ID);
    }
}
