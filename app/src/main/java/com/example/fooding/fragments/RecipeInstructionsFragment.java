package com.example.fooding.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fooding.R;
import com.example.fooding.models.ExtendedRecipe;

@SuppressWarnings({"ConstantConditions", "unused"})
public class RecipeInstructionsFragment extends Fragment {

    public static final String FOOD_ARG = "FOOD_ID_ARG";
    private static final String IMAGE_URL = "https://fullbellysisters.blogspot.com/";

    public ExtendedRecipe extendedRecipe;

    public RecipeInstructionsFragment() {
    }

    public static RecipeInstructionsFragment newInstance(ExtendedRecipe food) {
        RecipeInstructionsFragment fragment = new RecipeInstructionsFragment();
        Bundle args = new Bundle();
        args.putParcelable(FOOD_ARG, food);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_instructions, container, false);
        WebView webView = (WebView) view.findViewById(R.id.instructionsWebview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(IMAGE_URL);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        extendedRecipe = getArguments().getParcelable(FOOD_ARG);

    }
}
