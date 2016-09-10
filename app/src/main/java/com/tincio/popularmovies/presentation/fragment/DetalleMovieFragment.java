package com.tincio.popularmovies.presentation.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tincio.popularmovies.R;
import com.tincio.popularmovies.presentation.adapter.AdapterRecyclerDetailMovie;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetalleMovieFragment extends Fragment {

    AdapterRecyclerDetailMovie adapterRecyclerDetailMovie;
    LinearLayoutManager linearLayoutManager;
    @BindView(R.id.contentscrolling_recycler_trailers)
    RecyclerView recTrailers;
    @BindView(R.id.fragmentdetallemovie_fab_addfavorite)
    FloatingActionButton fabAddFavorito;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    Boolean favorito = false;
    public DetalleMovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detalle_movie, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String[] arrays = new String[10];
        adapterRecyclerDetailMovie = new AdapterRecyclerDetailMovie(Arrays.asList(arrays));
        recTrailers.setAdapter(adapterRecyclerDetailMovie);
        linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false);
        recTrailers.setLayoutManager(linearLayoutManager);
        collapsingToolbarLayout.setTitle("Titulo prueba");
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(android.R.color.white));
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.white));
    }

    @OnClick(R.id.fragmentdetallemovie_fab_addfavorite)
    void onClickAddFavorito(){
        try{
            if(favorito){
                fabAddFavorito.setImageDrawable(getResources().getDrawable(R.mipmap.ic_favorite_border_white_24dp));
            }else{
                fabAddFavorito.setImageDrawable(getResources().getDrawable(R.mipmap.ic_favorite_white_24dp));
            }
            favorito = !favorito;

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
