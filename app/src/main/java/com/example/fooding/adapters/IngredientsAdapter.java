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
import com.example.fooding.models.ExtendedIngredients;

import java.util.List;

@SuppressWarnings("ALL")
public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {
    private List<ExtendedIngredients> ingredients;
    private Context context;

    public IngredientsAdapter(List<ExtendedIngredients> ingredients) {
        this.ingredients = ingredients;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View ingredientView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredients_row_layout, parent, false);
        return new ViewHolder(ingredientView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ExtendedIngredients ingredient = ingredients.get(position);
        holder.bind(ingredient);
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView ingredientNameTextView;
        ImageView ingredientImageView;
        TextView ingredientUnitTextView;
        TextView ingredientAmountTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredientNameTextView = itemView.findViewById(R.id.ingredientName);
            ingredientImageView = itemView.findViewById(R.id.ingredient_imageview);
            ingredientUnitTextView = itemView.findViewById(R.id.ingredient_unit);
            ingredientAmountTextView = itemView.findViewById(R.id.ingredient_amount);
        }

        public void bind(ExtendedIngredients ingredient) {
            ingredientNameTextView.setText(ingredient.getName());
            ingredientUnitTextView.setText(ingredient.getUnit());
            ingredientAmountTextView.setText(ingredient.getAmount());
            Glide.with(ingredientImageView.getContext()).load(ingredient.getImage()).into(ingredientImageView);
        }
    }
}
