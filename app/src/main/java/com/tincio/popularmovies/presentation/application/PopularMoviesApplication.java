package com.tincio.popularmovies.presentation.application;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by innovagmd on 10/09/16.
 */
public class PopularMoviesApplication extends Application {


    RequestQueue requestQueue;
    public static PopularMoviesApplication mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        this.mApplication = this;
    }

    public RequestQueue getRequestQueue(){
        try{
            if(requestQueue == null){
                requestQueue = Volley.newRequestQueue(getApplicationContext());
            }
        }catch(Exception e){
            throw e;
        }
        return requestQueue;
    }

    public void cancelPendingRequest(Object tag){
        try{
            if(requestQueue !=null){
                requestQueue.cancelAll(tag);
            }
        }catch(Exception e){
            throw e;
        }
    }
}
