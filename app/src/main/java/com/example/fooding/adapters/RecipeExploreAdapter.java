package com.example.fooding.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fooding.R;
import com.example.fooding.models.Food;

import java.util.List;

public class RecipeExploreAdapter extends RecyclerView.Adapter<RecipeExploreAdapter.ViewHolder> implements View.OnClickListener {

    final RecipeExploreAdapterListener listener;
    final List<Food> foodList;

    public RecipeExploreAdapter(List<Food> foods, RecipeExploreAdapterListener listener) {
        this.foodList = foods;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View FoodView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_childrecyclerview, parent, false);
        return new ViewHolder(FoodView);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Food food = foodList.get(position);
        holder.bind(food);

    }

    @Override

    public int getItemCount() {
        return foodList.size();
    }

    @Override
    public void onClick(View v) {

    }

    public interface RecipeExploreAdapterListener {
        void onRecipeClicked(Food food);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        final TextView tvTitle;
        final ImageView ivImage;
        final CardView cardView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.recipeName);
            ivImage = itemView.findViewById(R.id.recipeImage);
            cardView = itemView.findViewById(R.id.recipeCardView);
        }

        public void bind(Food food) {
            tvTitle.setText(food.getTitle());

            Glide.with(ivImage.getContext()).load(food.getImage()).into(ivImage);
            cardView.setOnClickListener(view -> {
                if (listener != null) {
                    listener.onRecipeClicked(food);
                }
            });

        }
    }

}