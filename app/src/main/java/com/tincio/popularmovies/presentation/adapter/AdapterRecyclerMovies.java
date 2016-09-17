package com.tincio.popularmovies.presentation.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.squareup.picasso.Picasso;
import com.tincio.popularmovies.R;
import com.tincio.popularmovies.data.services.response.Result;
import com.tincio.popularmovies.presentation.application.PopularMoviesApplication;
import com.tincio.popularmovies.presentation.util.Constants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by tincio on 03/09/16.
 */
public class AdapterRecyclerMovies extends  RecyclerView.Adapter<AdapterRecyclerMovies.ViewHolderItem> {

    List<Result> listMovies;
    Context context;
    PopularMoviesApplication application = PopularMoviesApplication.mApplication;
    public AdapterRecyclerMovies(List<Result> arrayString) {
        this.listMovies = arrayString;
    }

    @Override
    public ViewHolderItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recycler_movies, parent, false);
        ViewHolderItem viewHolder = new ViewHolderItem(view);
        context = parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolderItem holder, int position) {
        holder.txtItemRecycler.setText(listMovies.get(position).getTitle());
        // Retrieves an image specified by the URL, displays it in the UI.
        Picasso.with(context).load(Constants.serviceNames.GET_IMAGE_MOVIES+listMovies.get(position).getPosterPath()).into(holder.imgMovie);
    }

    @Override
    public int getItemCount() {
        return listMovies.size();
    }

    public class ViewHolderItem extends RecyclerView.ViewHolder {

        @BindView(R.id.activity_gridlayout_txt)
        TextView txtItemRecycler;
        @BindView(R.id.activity_gridlayout_iconfavorito)
        ImageView iconfavorito;
        @BindView(R.id.rowrecyclermovie_img)
        ImageView imgMovie;
        String favoritoOff= "ic_favorite_border_white_24dp";
        String favoritoOn= "ic_favorite_white_24dp";
        int imagen ;

        public ViewHolderItem(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            iconfavorito.setTag(favoritoOff);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mOnItemClickListener!=null){
                        mOnItemClickListener.setOnItemClickListener(getPosition());
                    }
                }
            });
        }

        @OnClick(R.id.activity_gridlayout_iconfavorito)
        void changeIconFavorito(){
            if(iconfavorito.getTag().equals(favoritoOff)){
                imagen = context.getResources().getIdentifier(favoritoOn,"mipmap",context.getPackageName());
                iconfavorito.setTag(favoritoOn);
            }else{
                imagen = context.getResources().getIdentifier(favoritoOff,"mipmap",context.getPackageName());
                iconfavorito.setTag(favoritoOff);
            }
            iconfavorito.setImageDrawable(context.getResources().getDrawable(imagen));
        }
    }

    OnItemClickListener mOnItemClickListener;
    public interface OnItemClickListener{
        public void setOnItemClickListener(int posicion);
    }

    public void setOnItemClickListener(OnItemClickListener mItemClickListener){
        this.mOnItemClickListener = mItemClickListener;
    }


}