package com.tincio.topratedmovies.presentation.fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tincio.topratedmovies.R;
import com.tincio.topratedmovies.data.services.response.ResponseReviewsMovie;
import com.tincio.topratedmovies.data.services.response.ResponseTrailersMovie;
import com.tincio.topratedmovies.data.services.response.Result;
import com.tincio.topratedmovies.data.services.response.ResultTrailer;
import com.tincio.topratedmovies.presentation.adapter.AdapterRecyclerDetailMovie;
import com.tincio.topratedmovies.presentation.adapter.AdapterRecyclerReviewsMovie;
import com.tincio.topratedmovies.presentation.presenter.MovieTrailerPresenter;
import com.tincio.topratedmovies.presentation.util.Constants;
import com.tincio.topratedmovies.presentation.util.Utils;
import com.tincio.topratedmovies.presentation.view.MovieTrailerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetalleMovieFragment extends Fragment implements MovieTrailerView {

    AdapterRecyclerDetailMovie adapterRecyclerDetailMovie;
    LinearLayoutManager linearLayoutManager;
    @BindView(R.id.contentscrolling_recycler_trailers)
    RecyclerView recTrailers;
    @BindView(R.id.fragmentdetallemovie_fab_addfavorite)
    FloatingActionButton fabAddFavorito;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.fragmentdetallemovie_img)
    ImageView imgMovie;
    @BindView(R.id.contentscrolling_txt_descriptionmovie)
    TextView txtDescripcionMovie;
    @BindView(R.id.contentscrolling_txt_datemovie)
    TextView dateMovie;
    @BindView(R.id.contentscrolling_txt_average)
    TextView average;
    ProgressDialog progress;
    /**FOr reviews*/
    AdapterRecyclerReviewsMovie adapterRecyclerReviews;
    @BindView(R.id.contentscrolling_recycler_reviews)
    RecyclerView recReviews;
    LinearLayoutManager linearLayoutManagerReviews;

    MovieTrailerPresenter presenter;
    String favoritoOff= "ic_favorite_border_white_24dp";
    String favoritoOn= "ic_favorite_white_24dp";
    Result detailMovieSelection;

    public static String TAG = DetalleMovieFragment.class.getSimpleName();
    public DetalleMovieFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detalle_movie, container, false);
        ButterKnife.bind(this, view);
        presenter = new MovieTrailerPresenter();
        presenter.setView(this);
        return view;
    }

    public static Fragment newInstance(Bundle bundle){
        Fragment fragment = new DetalleMovieFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(android.R.color.white));
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.white));
        //get movie detail
        Result mResult=null;
        if(getArguments()!=null)
             mResult = (Result) getArguments().getSerializable(getResources().getString(R.string.serializable_detailmovie));
        if(mResult!=null)
            setDetailMovie(mResult);
    }

    public void setDetailMovie(Result detailMovie){
        if(detailMovie!=null){
            detailMovieSelection = detailMovie;
            collapsingToolbarLayout.setTitle(detailMovie.getTitle());
            Picasso.with(getActivity()).load(Constants.serviceNames.GET_IMAGE_MOVIES+detailMovie.getBackdropPath()).into(imgMovie);
            txtDescripcionMovie.setText(detailMovie.getOverview());
            dateMovie.setText(detailMovie.getReleaseDate());
            fabAddFavorito.setImageDrawable(Utils.getDrawableByName(getContext(),detailMovie.getFavorito()?favoritoOn:favoritoOff));
            fabAddFavorito.setTag(detailMovie.getFavorito()?favoritoOn:favoritoOff);
            presenter.getTrailerByMovie(detailMovie.getId());
            if(detailMovie.getVoteAverage() != null){
                average.setText("Average: "+detailMovie.getVoteAverage().toString());
            }

        }
    }

    @Override
    public void showLoading() {
        progress = Utils.showProgressDialog(getActivity());
    }

    @Override
    public void closeLoading() {
        if(progress!=null)
            progress.dismiss();
    }

    @Override
    public void showMovieTrailer(ResponseTrailersMovie detailMovie, String responseError) {
        adapterRecyclerDetailMovie = new AdapterRecyclerDetailMovie(detailMovie==null?null:detailMovie.getResults());
        recTrailers.setAdapter(adapterRecyclerDetailMovie);
        linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false);
        recTrailers.setLayoutManager(linearLayoutManager);
        adapterRecyclerDetailMovie.setOnItemClickListener(new AdapterRecyclerDetailMovie.OnItemClickListener() {
            @Override
            public void setOnItemClickListener(ResultTrailer trailer) {
                getIntentWatchTrailer(trailer.getKey());
            }
        });
        recTrailers.setNestedScrollingEnabled(false);
        recTrailers.setHasFixedSize(true);
    }

    @Override
    public void showResultFavorite(String response) {
        try {
            if(response.equals(getString(R.string.response_succesfull))){
                if (fabAddFavorito.getTag().equals(favoritoOn)) {
                    fabAddFavorito.setImageDrawable(getResources().getDrawable(R.mipmap.ic_favorite_border_white_24dp));
                    fabAddFavorito.setTag(favoritoOff);
                    detailMovieSelection.setFavorito(false);
                } else {
                    fabAddFavorito.setImageDrawable(getResources().getDrawable(R.mipmap.ic_favorite_white_24dp));
                    fabAddFavorito.setTag(favoritoOn);
                    detailMovieSelection.setFavorito(true);
                }
            }
            changeItemOfBackFragment();
        } catch(Exception e){
           e.printStackTrace();
        }
    }

    @Override
    public void showMovieReviews(ResponseReviewsMovie detailMovie, String responseError) {
        adapterRecyclerReviews = new AdapterRecyclerReviewsMovie(detailMovie==null?null:detailMovie.getResults());
        linearLayoutManagerReviews = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false);
        recReviews.setHasFixedSize(true);
        recReviews.setLayoutManager(linearLayoutManagerReviews);
        recReviews.setAdapter(adapterRecyclerReviews);
        recReviews.setNestedScrollingEnabled(false);

    }

    void getIntentWatchTrailer(String key){
        try{
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v="+key));
            startActivity(intent);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @OnClick(R.id.fragmentdetallemovie_fab_addfavorite)
    void onChangeStateFavorite(){
        try{
            presenter.saveFavoriteMovie(detailMovieSelection);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    void changeItemOfBackFragment(){
        try{
            ListMoviesFragment fragment = (ListMoviesFragment)getFragmentManager().findFragmentByTag(ListMoviesFragment.TAG);
            fragment.updateItemOfRecycler(detailMovieSelection);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
