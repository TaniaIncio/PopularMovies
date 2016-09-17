package com.tincio.popularmovies.presentation.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tincio.popularmovies.R;
import com.tincio.popularmovies.data.services.response.ResponseMovies;
import com.tincio.popularmovies.data.services.response.Result;
import com.tincio.popularmovies.presentation.adapter.AdapterRecyclerMovies;
import com.tincio.popularmovies.presentation.presenter.ListMoviePresenter;
import com.tincio.popularmovies.presentation.util.Utils;
import com.tincio.popularmovies.presentation.view.ListMovieView;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListMoviesFragment extends Fragment implements ListMovieView {

    private static String TAG = ListMoviesFragment.class.getSimpleName();

    @BindView(R.id.activity_gridlayout_recycler)
    RecyclerView recImageMovie;
    private GridLayoutManager gridLayoutManager;
    AdapterRecyclerMovies adapterRecycler;
    private Unbinder unbinder;
    ProgressDialog progress;
    ListMoviePresenter presenter;
    public ListMoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_list_movies, container, false);
        unbinder=ButterKnife.bind(this,view);
        presenter = new ListMoviePresenter();
        presenter.setView(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recImageMovie.setHasFixedSize(true);
        recImageMovie.setLayoutManager(gridLayoutManager);

        presenter.callListMovie();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    void changeFragment(Result movie){
        FragmentManager fm = getActivity().getSupportFragmentManager();
        Bundle bundle = new Bundle();
        bundle.putSerializable(getResources().getString(R.string.serializable_detailmovie), movie);
        Fragment fragment = DetalleMovieFragment.newInstance(bundle);
        FragmentTransaction fragmentTransaction =
                fm.beginTransaction().replace(R.id.fragment_base, fragment);
        fragmentTransaction.addToBackStack(TAG);
        fragmentTransaction.commit();
        fm.executePendingTransactions();

    }

    @Override
    public void showListMovies(ResponseMovies responseMovies, String responseError) {
        adapterRecycler = new AdapterRecyclerMovies(responseMovies.getResults());
        recImageMovie.setAdapter(adapterRecycler);
        adapterRecycler.setOnItemClickListener(new AdapterRecyclerMovies.OnItemClickListener() {
            @Override
            public void setOnItemClickListener(Result movie) {
                changeFragment(movie);
            }
        });
        //for favorite
        adapterRecycler.setOnItemClickListenerFavorite(new AdapterRecyclerMovies.OnItemClickListenerFavorite() {
            @Override
            public void setOnItemClickListener(Result movie) {
                presenter.saveFavoriteMovie(movie.getId());
            }
        });
    }

    @Override
    public void showResultFavorite(String response) {
        Log.i(TAG, response);
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
}
