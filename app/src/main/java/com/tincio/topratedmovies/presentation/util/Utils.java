package com.tincio.topratedmovies.presentation.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import com.tincio.topratedmovies.R;

/**
 * Created by tincio on 12/09/2016.
 */
public class Utils {

    public static ProgressDialog showProgressDialog(Context ctx) {
        ProgressDialog dialog;
        dialog=ProgressDialog.show(ctx,null, null);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.progress_layout);

        return dialog;
    }

    public static Drawable getDrawableByName(Context context, String name){
        return context.getResources().getDrawable(context.getResources().getIdentifier(name,"mipmap",context.getPackageName()));
    }
}
