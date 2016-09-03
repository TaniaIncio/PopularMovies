package com.tincio.popularmovies.presentation.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tincio.popularmovies.R;
import com.tincio.popularmovies.presentation.adapter.AdapterRecyclerMovies;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListMoviesFragment extends Fragment {

    @BindView(R.id.activity_gridlayout_recycler)
    RecyclerView recImageMovie;
    private GridLayoutManager gridLayoutManager;
    AdapterRecyclerMovies adapterRecycler;
    private Unbinder unbinder;
    public ListMoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_list_movies, container, false);
        unbinder=ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recImageMovie.setHasFixedSize(true);
        recImageMovie.setLayoutManager(gridLayoutManager);
        String[] array = {"Interestelar1","Interestelar2","Interestelar3","Interestelar4",
                "Interestelar1","Interestelar2","Interestelar3","Interestelar4",
                "Interestelar1","Interestelar2","Interestelar3","Interestelar4"};
        adapterRecycler = new AdapterRecyclerMovies(Arrays.asList(array));
        recImageMovie.setAdapter(adapterRecycler);

        adapterRecycler.setOnItemClickListener(new AdapterRecyclerMovies.OnItemClickListener() {
            @Override
            public void setOnItemClickListener(int posicion) {
                changeFragment();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    void changeFragment(){
        FragmentTransaction fragmentTransaction =
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_base, new DetalleMovieFragment());
        fragmentTransaction.addToBackStack("");
        fragmentTransaction.commit();

    }
}
