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
import com.example.fooding.models.IngredientSearchSuggestion;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
public class SuggestionsAdapter extends RecyclerView.Adapter<SuggestionsAdapter.ViewHolder> {

    public List<IngredientSearchSuggestion> suggestions = new ArrayList<>();
    public SuggestionAdapterListener listener = null;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View ingredientView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingredient_search, parent, false);
        return new ViewHolder(ingredientView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(suggestions.get(position));
    }

    @Override
    public int getItemCount() {
        if (suggestions == null) return 0;
        return suggestions.size();
    }

    public interface SuggestionAdapterListener {
        void onSuggestionClicked(IngredientSearchSuggestion suggestion);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView ingredientTextView;
        ImageView ingredientImageView;
        Context context;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredientImageView = itemView.findViewById(R.id.ivIngredientImage);
            ingredientTextView = itemView.findViewById(R.id.tvIngredientName);
            this.context = context;
        }

        public void bind(IngredientSearchSuggestion suggestion) {
            ingredientTextView.setText(suggestion.name);
            Glide.with(ingredientImageView.getContext()).load(suggestion.getImage()).into(ingredientImageView);
            ingredientTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onSuggestionClicked(suggestion);
                    }
                }
            });
        }
    }
}
