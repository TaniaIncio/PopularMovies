package com.tincio.popularmovies.presentation.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;

import com.tincio.popularmovies.R;

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
}
