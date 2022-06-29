package adapters;

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
import com.example.fooding.models.food;

import java.util.List;

public class foodadapter extends RecyclerView.Adapter <foodadapter.ViewHolder>{

    Context context;
    List<food> Foods;

    public foodadapter(Context context, List<food> Foods) {
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
        food Food = Foods.get(position);

        holder.bind(Food);

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

        public void bind(food Food) {
            tvTitle.setText(Food.getTitle());
            Glide.with(context).load(Food.getImage()).into(ivImage);
        }
    }

}
