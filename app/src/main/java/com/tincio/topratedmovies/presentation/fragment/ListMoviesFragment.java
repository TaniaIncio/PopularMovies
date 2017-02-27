package com.tincio.topratedmovies.presentation.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.tincio.topratedmovies.R;
import com.tincio.topratedmovies.data.services.response.ResponseMovies;
import com.tincio.topratedmovies.data.services.response.Result;
import com.tincio.topratedmovies.presentation.adapter.AdapterRecyclerMovies;
import com.tincio.topratedmovies.presentation.adapter.EndlessRecyclerOnScrollListener;
import com.tincio.topratedmovies.presentation.presenter.ListMoviePresenter;
import com.tincio.topratedmovies.presentation.util.Utils;
import com.tincio.topratedmovies.presentation.view.ListMovieView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListMoviesFragment extends Fragment implements ListMovieView, AdapterView.OnItemSelectedListener {

    public static String TAG = ListMoviesFragment.class.getSimpleName();

    @BindView(R.id.activity_gridlayout_recycler)
    RecyclerView recImageMovie;
    private GridLayoutManager gridLayoutManager;
    AdapterRecyclerMovies adapterRecycler;
    private Unbinder unbinder;
    ProgressDialog progress;
    ListMoviePresenter presenter;
    Integer positionSelection;
    Result movieSelection;
    @BindView(R.id.spinner_order_movies)
    Spinner spinnerOrderMovies;
    @BindView(R.id.bottom_banner)
    AdView mBottomBanner;
    String OPTION;
    Integer CURRENT_PAGE = 1;
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

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),R.array.order_movies,R.layout.row_spinner_ordermovies);
        spinnerOrderMovies.setAdapter(adapter);
        spinnerOrderMovies.setOnItemSelectedListener(this);

        gridLayoutManager = new GridLayoutManager(getActivity(), 2  );
        recImageMovie.setHasFixedSize(true);
        recImageMovie.setLayoutManager(gridLayoutManager);
        scrollEndRecyclerList();

        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("B1B30FBDDBEF32919AF6C5099897C2FA")//AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mBottomBanner.loadAd(adRequest);

        return view;
    }

    void scrollEndRecyclerList() {
        try {
            // LinearLayoutManager linearLayoutManager =new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false);
            // recInstaladores.setLayoutManager(linearLayoutManager);
        //    recImageMovie.OnScrollListener(new RecyclerView.OnScrollListener())
            recImageMovie.addOnScrollListener(new EndlessRecyclerOnScrollListener(gridLayoutManager) {
                @Override
                public void onLoadMore(int current_page) {
                    Log.v(TAG + "currentpage", "currentpage " + current_page);
                    CURRENT_PAGE  = current_page;
                    if(!OPTION.equals(getString(R.string.id_order_three))){
                        presenter.callListMovie(OPTION,current_page);
                    }

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

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
                fm.beginTransaction().replace(R.id.fragment_base, fragment, DetalleMovieFragment.TAG);
        fragmentTransaction.addToBackStack(TAG);
        fragmentTransaction.commit();
        fm.executePendingTransactions();

    }

    @Override
    public void showListMovies(ResponseMovies responseMovies, String responseError) {
        List<Result> list =null;
        if (adapterRecycler!=null){
            list = adapterRecycler.getListMovies();//
            if (list!=null)
                list.addAll(responseMovies.getResults());
        }
        if (CURRENT_PAGE == 1){
            adapterRecycler = new AdapterRecyclerMovies((responseMovies==null?null:(list==null?responseMovies.getResults():list)));
            recImageMovie.setAdapter(adapterRecycler);
        }else{
            adapterRecycler.setListMovies(list);
        }

        adapterRecycler.setOnItemClickListener(new AdapterRecyclerMovies.OnItemClickListener() {
            @Override
            public void setOnItemClickListener(Result movie, Integer position) {
                positionSelection = position;
                if(getResources().getBoolean(R.bool.has_two_panes)){
                    DetalleMovieFragment fragment = (DetalleMovieFragment)getFragmentManager().findFragmentByTag(DetalleMovieFragment.TAG);
                    fragment.setDetailMovie(movie);
                }else
                    changeFragment(movie);
                }
        });
        scrollEndRecyclerList();
        //for favorite
        adapterRecycler.setOnItemClickListenerFavorite(new AdapterRecyclerMovies.OnItemClickListenerFavorite() {
            @Override
            public void setOnItemClickListener(Result movie, Integer indice) {
                positionSelection = indice;
                movieSelection = movie;
                presenter.saveFavoriteMovie(movie);
                if(getResources().getBoolean(R.bool.has_two_panes)){
                    DetalleMovieFragment fragment = (DetalleMovieFragment)getFragmentManager().findFragmentByTag(DetalleMovieFragment.TAG);
                    fragment.showResultFavorite(getString(R.string.response_succesfull));
                }

            }
        });
    }

    @Override
    public void showResultFavorite(String response) {
        if(response.equals(getString(R.string.response_succesfull))){
            movieSelection.setFavorito(!movieSelection.getFavorito());
            if(OPTION.equals(getString(R.string.id_order_three))){
                adapterRecycler.getListMovies().remove(movieSelection);
                adapterRecycler.notifyDataSetChanged();
            }else{
                adapterRecycler.updateItemList(positionSelection, movieSelection);
            }

        }
    }

    @Override
    public void showLoading() {
        Log.i(TAG, "showLoading");
        progress = Utils.showProgressDialog(getActivity());
    }

    @Override
    public void closeLoading() {
        if(progress!=null)
            progress.dismiss();
    }

    //update item row of recycler
    public void updateItemOfRecycler(Result movie){
        adapterRecycler.updateItem(positionSelection, movie);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        adapterRecycler = null;
        CURRENT_PAGE = 1;
        if(i==0) {
            presenter.callListMovie(getString(R.string.id_order_one), 1);
            OPTION = getString(R.string.id_order_one);
        }else if(i == 1){
            OPTION = getString(R.string.id_order_two);
            presenter.callListMovie(getString(R.string.id_order_two),1);
        } else {
            presenter.callListMovie(getString(R.string.id_order_three), 1);
            OPTION = getString(R.string.id_order_three);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (mBottomBanner != null) {
            mBottomBanner.resume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mBottomBanner != null) {
            mBottomBanner.pause();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mBottomBanner != null) {
            mBottomBanner.destroy();
        }
    }
}
