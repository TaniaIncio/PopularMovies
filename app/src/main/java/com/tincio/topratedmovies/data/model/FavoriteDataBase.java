package com.tincio.topratedmovies.data.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tincio.topratedmovies.presentation.util.Constants;

/**
 * Created by tincio on 08/02/17.
 */

public class FavoriteDataBase extends SQLiteOpenHelper {

    String sqlCreate = "CREATE TABLE Favorite " +
            "(ID_ INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "id_movie TEXT, nombre TEXT , path TEXT, overview TEXT, releaseDate TEXT, " +
            "originalTitle TEXT, originalLanguage TEXT, backdropPath TEXT, popularity REAL, " +
            " voteCount INTEGER, voteAverage NUMERIC)";
    static Integer VERSION = 1;
    static String name = "db_favorite";
    public FavoriteDataBase(Context context) {
        super(context, name, null, VERSION);
    }

    public void deleteFavorite(Integer idFavorite){
        String where = "id_movie = ?";
        String[] params =new String[]{idFavorite.toString()};
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        sqLiteDatabase.delete(Constants.TABLE_FAVORITE, where, params);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + Constants.TABLE_FAVORITE);
        db.execSQL(sqlCreate);
    }


}
