package com.example.fooding.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fooding.R;
import com.example.fooding.models.Food;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> implements View.OnClickListener {

    final FoodAdapterListener listener;
    final List<Food> foods;

    public FoodAdapter(List<Food> foods, FoodAdapterListener listener) {
        this.foods = foods;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View FoodView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false);
        return new ViewHolder(FoodView);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Food food = foods.get(position);
        holder.bind(food);

    }

    @Override

    public int getItemCount() {
        return foods.size();
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

        public void bind(Food food) {
            tvTitle.setText(food.getTitle());
            Glide.with(ivImage.getContext()).load(food.getImage()).into(ivImage);
            cardView.setOnClickListener(view -> {
                if (listener != null) {
                    listener.onFoodClicked(food);
                }
            });

        }
    }

    public interface FoodAdapterListener {
        void onFoodClicked(Food food);
    }


}
