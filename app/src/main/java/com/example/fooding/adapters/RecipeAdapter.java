package com.example.fooding.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fooding.R;
import com.example.fooding.models.Recipe;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> implements View.OnClickListener {

    final FoodAdapterListener listener;
    final List<Recipe> recipes;

    public RecipeAdapter(List<Recipe> recipes, FoodAdapterListener listener) {
        this.recipes = recipes;
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
        Recipe recipe = recipes.get(position);
        holder.bind(recipe);
        holder.cardView.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.anim1));

    }

    @Override

    public int getItemCount() {
        if (recipes != null) {
            return recipes.size();
        }
        return 1;
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

        public void bind(Recipe recipe) {
            tvTitle.setText(recipe.getTitle());
            Glide.with(ivImage.getContext()).load(recipe.getImage()).into(ivImage);
            cardView.setOnClickListener(view -> {
                if (listener != null) {
                    listener.onFoodClicked(recipe);
                }
            });

        }
    }

    public interface FoodAdapterListener {
        void onFoodClicked(Recipe recipe);
    }
}
