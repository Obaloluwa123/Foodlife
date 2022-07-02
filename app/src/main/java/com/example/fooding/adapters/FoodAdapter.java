package com.example.fooding.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fooding.R;
import com.example.fooding.activities.DetailActivity;
import com.example.fooding.models.Food;

import org.parceler.Parcel;
import org.parceler.Parcels;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {

    final Context context;
    final List<Food> Foods;

    public FoodAdapter(Context context, List<Food> Foods) {
        this.context = context;
        this.Foods = Foods;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("foodadapter", "onCreateViewHolder");
        View FoodView = LayoutInflater.from(context).inflate(R.layout.item_food, parent, false);
        return new ViewHolder(FoodView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("foodadapter", "onBindViewHolder" + position);
        Food food = Foods.get(position);
        holder.bind(food);
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("Overview", food.getTitle());
                bundle.putString("Ingredients", food.getImage());
                bundle.putString("Instructions", food.getTitle());

                intent.putExtras(bundle);
                context.startActivity(intent);


            }
        });

    }

    @Override

    public int getItemCount() {
        return Foods.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final TextView tvTitle;
        final ImageView ivImage;
        ConstraintLayout constraintLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            ivImage = itemView.findViewById(R.id.ivImage);
            constraintLayout = itemView.findViewById(R.id.main_layout);
            itemView.setOnClickListener(this);

        }

        public void bind(Food food) {
            tvTitle.setText(food.getTitle());
            Glide.with(context).load(food.getImage()).into(ivImage);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();

            if (position != RecyclerView.NO_POSITION) {
                Food food = Foods.get(position);
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(Food.class.getSimpleName(), String.valueOf(Parcels.wrap(food)));
                context.startActivity(intent);
            }
        }
    }
}
