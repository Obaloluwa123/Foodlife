package com.example.fooding.adapters;

import android.content.Context;
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

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter <FoodAdapter.ViewHolder>{

    Context context;
    List<Food> Foods;

    public FoodAdapter(Context context, List<Food> Foods) {
        this.context = context;
        this.Foods = Foods;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d ("foodadapter", "onCreateViewHolder");
        View FoodView = LayoutInflater.from(context).inflate(R.layout.item_food, parent, false);
        return new ViewHolder(FoodView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d ("foodadapter", "onBindViewHolder" + position);
        Food food = Foods.get(position);

//        holder.cardView.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.anim1));
        holder.bind(food);

    }

    @Override

    public int getItemCount() {return Foods.size();}

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        ImageView ivImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            ivImage = itemView.findViewById(R.id.ivImage);

        }

        public void bind(Food food) {
            tvTitle.setText(food.getTitle());
            Glide.with(context).load(food.getImage()).into(ivImage);
        }
    }

}
