package com.example.fooding.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fooding.R;
import com.example.fooding.activities.LoginActivity;
import com.parse.ParseUser;

public class ProfileFragment extends Fragment {

    public ProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button logoutBtn = view.findViewById(R.id.btnLogout);
        TextView username = view.findViewById(R.id.username);
        TextView userEmail = view.findViewById(R.id.userEmail);

        if (ParseUser.getCurrentUser() != null) {
            username.setText(ParseUser.getCurrentUser().getUsername());
            userEmail.setText(ParseUser.getCurrentUser().getEmail());
        }

        logoutBtn.setOnClickListener(v -> {
            ParseUser.logOutInBackground();
            ParseUser currentUser = ParseUser.getCurrentUser();
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
        });

    }
}