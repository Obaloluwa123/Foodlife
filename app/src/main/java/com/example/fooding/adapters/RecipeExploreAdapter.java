package com.example.fooding.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fooding.R;
import com.example.fooding.models.Ingredients;

import java.util.List;

public class RecipeExploreAdapter extends RecyclerView.Adapter<RecipeExploreAdapter.ViewHolder> {

    Context context;
    public List<Ingredients> recipeByIngredients;


    public RecipeExploreAdapter(Context context, List<Ingredients> recipeByIngredients) {
        this.context = context;
        this.recipeByIngredients = recipeByIngredients;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View recipeView = LayoutInflater.from(context).inflate(R.layout.item_explore, parent, false);
        return new ViewHolder(recipeView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ingredients ingredient = recipeByIngredients.get(position);
        holder.bind(ingredient);
    }

    @NonNull
    @Override
    public int getItemCount() {
        if (recipeByIngredients == null) return 0;
        return recipeByIngredients.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView;
        ImageView recipeImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.recipeTextView);
            recipeImageView = itemView.findViewById(R.id.recipeImageView);
        }

        public void bind(Ingredients ingredient) {
            titleTextView.setText(ingredient.getName());
            Glide.with(context).load(ingredient.getImage()).into(recipeImageView);
        }
    }
}