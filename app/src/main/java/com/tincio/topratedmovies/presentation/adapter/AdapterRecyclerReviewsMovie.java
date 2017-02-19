package com.tincio.topratedmovies.presentation.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tincio.topratedmovies.R;
import com.tincio.topratedmovies.data.services.response.ResultReviews;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tincio on 22/07/2016.
 */
public class AdapterRecyclerReviewsMovie extends RecyclerView.Adapter<AdapterRecyclerReviewsMovie.ViewHolderItem> {

    List<ResultReviews> listReviews;


    public AdapterRecyclerReviewsMovie(List listReviews){
        this.listReviews = listReviews;
    }
    @Override
    public ViewHolderItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recycler_reviews, parent, false);
        ViewHolderItem viewHolder = new ViewHolderItem(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolderItem holder, int position) {
        ResultReviews mReview = listReviews.get(position);
        holder.txtNombreUser.setText(mReview.getAuthor());
        holder.txtReview.setText(mReview.getContent());
    }

    @Override
    public int getItemCount() {
        return listReviews==null?0:listReviews.size();
    }

    public class ViewHolderItem extends RecyclerView.ViewHolder {


        @BindView(R.id.rowrecyclerdetalle_txt_user)
        TextView txtNombreUser;
        @BindView(R.id.rowrecyclerdetalle_txt_review)
        TextView txtReview;

        public ViewHolderItem(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

    }

}
