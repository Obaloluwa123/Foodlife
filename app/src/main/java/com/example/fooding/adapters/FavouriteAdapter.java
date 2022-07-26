package com.example.fooding.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fooding.R;
import com.example.fooding.favourite.FavouriteList;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.ViewHolder> implements View.OnClickListener {

    final List<FavouriteList> favouriteLists;
    final FavouriteAdapterListener listener;

    public FavouriteAdapter(List<FavouriteList> favouriteLists, FavouriteAdapterListener listener) {

        this.favouriteLists = favouriteLists;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FavouriteList data = favouriteLists.get(position);

        holder.bind(data);
    }

    @Override
    public int getItemCount() {
        return favouriteLists.size();
    }

    @Override
    public void onClick(View v) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tvTitle;
        final ImageView ivImage;
        final MaterialCardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            ivImage = itemView.findViewById(R.id.ivImage);
            cardView = itemView.findViewById(R.id.cardView);
        }

        public void bind(FavouriteList favouriteList) {
            tvTitle.setText(favouriteList.getTitle());
            Glide.with(ivImage.getContext()).load(favouriteList.getImage()).into(ivImage);
            cardView.setOnClickListener(view -> {
                if (listener != null) {
                    listener.onFavouriteFoodClicked(favouriteList);
                }
            });

        }
    }

    public interface FavouriteAdapterListener {
        void onFavouriteFoodClicked(FavouriteList favouriteList);
    }
}
