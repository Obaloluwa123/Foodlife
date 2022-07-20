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
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.ProfilePictureView;
import com.parse.ParseUser;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfileFragment extends Fragment {

    CallbackManager callbackManager;

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
        callbackManager = CallbackManager.Factory.create();
        Button logoutBtn = view.findViewById(R.id.btnLogout);
        TextView username = view.findViewById(R.id.username);
        ProfilePictureView profileImage = view.findViewById(R.id.profileImage);


        if (ParseUser.getCurrentUser() != null) {
            username.setText(ParseUser.getCurrentUser().getUsername());
        }

        Bundle bundle = new Bundle();
        bundle.putString("fields", "gender, name , id");

        AccessToken accessToken = AccessToken.getCurrentAccessToken();

                if (accessToken != null) {
                    GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(@Nullable JSONObject jsonObject, @Nullable GraphResponse graphResponse) {
                            try {
                                String id = jsonObject.getString("id");
                                new GraphRequest(
                                        AccessToken.getCurrentAccessToken(),
                                        "/{user-id}/picture",
                                        null,
                                        HttpMethod.GET,
                                        new GraphRequest.Callback() {
                                            public void onCompleted(GraphResponse response) {

                                            }
                                        }
                                ).executeAsync();
                                profileImage.setDrawingCacheEnabled(true);
                                profileImage.setProfileId(id);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                    });
                    graphRequest.setParameters(bundle);
                    graphRequest.executeAsync();

        }
        logoutBtn.setOnClickListener(v -> {
            LoginManager.getInstance().logOut();
            profileImage.setProfileId("");
            ParseUser.logOutInBackground();
            ParseUser currentUser = ParseUser.getCurrentUser();
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
        });


    }
}