package com.example.fooding.fragments;

import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.fooding.R;
import com.example.fooding.activities.MainActivity;
import com.example.fooding.favourite.FavouriteList;
import com.example.fooding.models.FoodExtended;

@SuppressWarnings({"PointlessBooleanExpression", "StatementWithEmptyBody"})
public class OverviewFragment extends Fragment {

    public static final String FOOD_ARG = "FOOD_ID_ARG";

    public FoodExtended foodExtended;

    public static OverviewFragment newInstance(FoodExtended food) {
        OverviewFragment fragment = new OverviewFragment();
        Bundle args = new Bundle();
        args.putParcelable(FOOD_ARG, food);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_overview, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textView = view.findViewById(R.id.title_textView);
        ImageView imageView = view.findViewById(R.id.ivImage);
        ImageView likeButtonImageView = view.findViewById(R.id.like_imageView);
        TextView summaryTextView = view.findViewById(R.id.summary_textView);
        foodExtended = requireArguments().getParcelable(FOOD_ARG);

        textView.setText(foodExtended.title);
        summaryTextView.setText(foodExtended.summary);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            summaryTextView.setText(HtmlCompat.fromHtml(foodExtended.summary, HtmlCompat.FROM_HTML_MODE_LEGACY));
        } else {
            summaryTextView.setText(Html.fromHtml(foodExtended.summary));
        }

        if (MainActivity.favouriteDatabase.favouriteDao().exists(String.valueOf(foodExtended.id))) {
            likeButtonImageView.setBackground(getContext().getDrawable(R.drawable.ic_blue_heart));
        } else {
            likeButtonImageView.setBackground(getContext().getDrawable(R.drawable.ic_heart_button));

        }

        Glide.with(requireContext()).load(foodExtended.image).into(imageView);

        final Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.like_button_action);

        imageView.setOnClickListener(new DoubleClickListener() {
            @Override
            public void onDoubleClick() {
                likeButtonImageView.startAnimation(animation);
                FavouriteList favoriteList = new FavouriteList();

                String id = String.valueOf(foodExtended.id);
                String title = foodExtended.title;
                String image = foodExtended.image;


                favoriteList.setId(id);
                favoriteList.setTitle(title);
                favoriteList.setImage(image);

                if (MainActivity.favouriteDatabase.favouriteDao().exists(id)) {
                    MainActivity.favouriteDatabase.favouriteDao().delete(favoriteList);
                    likeButtonImageView.setBackground(getContext().getDrawable(R.drawable.ic_heart_button));
                } else {
                    MainActivity.favouriteDatabase.favouriteDao().addData(favoriteList);
                    likeButtonImageView.setBackground(getContext().getDrawable(R.drawable.ic_blue_heart));
                    Log.d("ID", id);
                }
            }
        });
    }

    public abstract static class DoubleClickListener implements View.OnClickListener {

        private static final long DEFAULT_QUALIFICATION_SPAN = 200;
        private final long doubleClickQualificationSpanInMillis;
        private long timestampLastClick;

        public DoubleClickListener() {
            doubleClickQualificationSpanInMillis = DEFAULT_QUALIFICATION_SPAN;
            timestampLastClick = 0;
        }

        public DoubleClickListener(long doubleClickQualificationSpanInMillis) {
            this.doubleClickQualificationSpanInMillis = doubleClickQualificationSpanInMillis;
            timestampLastClick = 0;
        }

        @Override
        public void onClick(View v) {
            if ((SystemClock.elapsedRealtime() - timestampLastClick) < doubleClickQualificationSpanInMillis) {
                onDoubleClick();
            }
            timestampLastClick = SystemClock.elapsedRealtime();
        }

        public abstract void onDoubleClick();

    }
}
