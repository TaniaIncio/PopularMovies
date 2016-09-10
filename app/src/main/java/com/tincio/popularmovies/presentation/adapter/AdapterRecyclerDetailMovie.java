package com.tincio.popularmovies.presentation.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tincio.popularmovies.R;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by tincio on 22/07/2016.
 */
public class AdapterRecyclerDetailMovie extends RecyclerView.Adapter<AdapterRecyclerDetailMovie.ViewHolderItem> {

    List arrayString;


    public AdapterRecyclerDetailMovie(List arrayString){
        this.arrayString = arrayString;
    }
    @Override
    public ViewHolderItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recycler_detallemovie, parent, false);
        ViewHolderItem viewHolder = new ViewHolderItem(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolderItem holder, int position) {
       // holder.txtNombreTrailer.setText(arrayString.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return arrayString.size();
    }

    public class ViewHolderItem extends RecyclerView.ViewHolder {

        @BindView(R.id.rowrecyclerdetalle_linear_nrotrailer)
        LinearLayout linearLayout;
        @BindView(R.id.rowrecyclerdetalle_txt_nombretrailer)
        TextView txtNombreTrailer;

        public ViewHolderItem(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        @OnClick(R.id.rowrecyclerdetalle_linear_nrotrailer)
        void onClickLinearTrailer(){
            try{
                if(mOnItemClickListener!=null){
                    mOnItemClickListener.setOnItemClickListener(getAdapterPosition());
                }
            }catch (Exception e){
                e.printStackTrace();
            }
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
