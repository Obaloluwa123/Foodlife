package com.example.fooding.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fooding.R;
import com.example.fooding.models.Food;
import com.example.fooding.models.RecipeParent;

import java.util.ArrayList;
//TODO:working on this Adapter for my explore page
public class RecipeParentAdapter extends RecyclerView.Adapter<RecipeParentAdapter.MyViewholder> {
    private ArrayList<RecipeParent> parentModelArrayList;
    public Context context;


    public RecipeParentAdapter(ArrayList<RecipeParent> parentModelArrayList, Context context) {
        this.parentModelArrayList = parentModelArrayList;
    }

    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_explore, parent, false);
        return new MyViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewholder holder, int position) {
        RecipeParent currentItem = parentModelArrayList.get(position);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        holder.parentRecyclerView.setLayoutManager(layoutManager);
        holder.parentRecyclerView.setHasFixedSize(true);

        holder.recipeCategory.setText(currentItem.recipeCategory());
        ArrayList<Food> arrayList = new ArrayList<>();
        if (parentModelArrayList.get(position).recipeCategory().equals("Recipe By Ingredients in my Fridge")) {

        }


    }

    @Override
    public int getItemCount() {
        return parentModelArrayList.size();
    }

    public class MyViewholder extends RecyclerView.ViewHolder {
        public TextView recipeCategory;
        public RecyclerView parentRecyclerView;

        public MyViewholder(View itemView) {
            super(itemView);

            recipeCategory = itemView.findViewById(R.id.recipeCategory);
            parentRecyclerView = itemView.findViewById(R.id.parentRecyclerView);
        }
    }

}
