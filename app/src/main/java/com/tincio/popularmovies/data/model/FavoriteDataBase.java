package com.tincio.popularmovies.data.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by tincio on 08/02/17.
 */

public class FavoriteDataBase extends SQLiteOpenHelper {

    String sqlCreate = "CREATE TABLE Favorite " +
            "(_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            " nombre TEXT)";
    public FavoriteDataBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(sqlCreate);
    }


}
