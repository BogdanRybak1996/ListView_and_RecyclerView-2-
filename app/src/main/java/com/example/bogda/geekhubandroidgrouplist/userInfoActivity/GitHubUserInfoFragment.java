package com.example.bogda.geekhubandroidgrouplist.userInfoActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bogda.geekhubandroidgrouplist.data.GitHubUser;
import com.example.bogda.geekhubandroidgrouplist.R;
import com.example.bogda.geekhubandroidgrouplist.data.GooglePlusUser.GooglePlusUser;
import com.example.bogda.geekhubandroidgrouplist.service.GitHubApi;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;


import okhttp3.Callback;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.bogda.geekhubandroidgrouplist.service.OnlineChecker.isOnline;

/**
 * Created by bohdan on 04.11.16.
 */

public class GitHubUserInfoFragment extends Fragment {

    GitHubUser user = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_git_hub_user_info, container, false);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        final String BASE_URL = "https://api.github.com";
        Retrofit client = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        GitHubApi api = client.create(GitHubApi.class);
        String[] urlParams = getActivity().getIntent().getData().toString().split("/");
        Call<GitHubUser> call = api.getUser(urlParams[urlParams.length - 1]);
        if (isOnline(getContext())) {
            call.enqueue(new retrofit2.Callback<GitHubUser>() {
                @Override
                public void onResponse(Call<GitHubUser> call, retrofit2.Response<GitHubUser> response) {
                    if (response.isSuccessful()) {
                        user = response.body();
                        getActivity().runOnUiThread(updateUIRunnable);
                    }
                    else{
                        checkUser();
                    }
                }

                @Override
                public void onFailure(Call<GitHubUser> call, Throwable t) {
                    Toast.makeText(getContext(), "Data get error", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getContext(), "Check the internet connection", Toast.LENGTH_SHORT).show();
            getActivity().finish();
            return;
        }

    }
    private final Runnable updateUIRunnable = new Runnable() {
        @Override
        public void run() {

            checkUser();

            //Show name
            TextView nameTextView = (TextView) getActivity().findViewById(R.id.git_hub_user_info_name);
            if (user.getName() != null) {
                nameTextView.setText(user.getName());
            } else {
                nameTextView.setVisibility(View.GONE);
            }

            //download image
            final ImageView photo = (ImageView) getActivity().findViewById(R.id.git_hub_user_info_photo);
            Picasso.with(getActivity()).load(user.getAvatar_url()).into(photo);


            //Show email
            TextView emailTextView = (TextView) getActivity().findViewById(R.id.git_hub_user_info_email);
            if (user.getEmail() != null) {
                emailTextView.setText(user.getEmail());
                Paint p = new Paint();
                p.setColor(Color.BLUE);
                emailTextView.setPaintFlags(p.getColor());
                emailTextView.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
                final String email = user.getEmail();
                emailTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/html");
                        intent.putExtra(Intent.EXTRA_EMAIL, email);
                        startActivity(intent);
                    }
                });
            } else {
                emailTextView.setVisibility(View.GONE);
            }

            //Show login
            TextView loginTextView = (TextView) getActivity().findViewById(R.id.git_hub_user_info_login);
            if (user.getLogin() != null) {
                loginTextView.setText(user.getLogin());
                Paint p = new Paint();
                p.setColor(Color.BLUE);
                loginTextView.setPaintFlags(p.getColor());
                loginTextView.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
                final String login = user.getLogin();
                loginTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("https://github.com/" + login));
                        startActivity(intent);
                    }
                });
            } else {
                loginTextView.setVisibility(View.GONE);
            }

            //Account type
            TextView typeTextView = (TextView) getActivity().findViewById(R.id.git_hub_user_info_type);
            typeTextView.setText(user.getType());

            //repos
            TextView reposTextView = (TextView) getActivity().findViewById(R.id.git_hub_user_info_repos);
            reposTextView.setText(Integer.toString(user.getPublic_repos()));

            //Location
            TextView locationTextView = (TextView) getActivity().findViewById(R.id.git_hub_user_info_location);
            if (user.getLocation() != null) {
                final String location = user.getLocation();
                locationTextView.setText(location);
                Paint p = new Paint();
                p.setColor(Color.BLUE);
                locationTextView.setPaintFlags(p.getColor());
                locationTextView.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
                locationTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + location));
                        startActivity(intent);
                    }
                });
            } else {
                locationTextView.setVisibility(View.GONE);
                TextView locationLabelTextView = (TextView) getActivity().findViewById(R.id.git_hub_user_info_location_label);
                locationLabelTextView.setVisibility(View.GONE);
            }

            //Followers
            TextView followersTextVeiew = (TextView) getActivity().findViewById(R.id.git_hub_user_info_followers);
            followersTextVeiew.setText(Integer.toString(user.getFollowers()));

            //Following
            TextView followingTextView = (TextView) getActivity().findViewById(R.id.git_hub_user_info_followoing);
            followingTextView.setText(Integer.toString(user.getFollowing()));

            //Biography
            TextView biographyTextView = (TextView) getActivity().findViewById(R.id.git_hub_user_info_biography);
            if (user.getBio() != null) {
                biographyTextView.setText(user.getBio());
            } else {
                TextView biographyLabelTextView = (TextView) getActivity().findViewById(R.id.git_hub_user_info_biography_label);
                biographyLabelTextView.setVisibility(View.GONE);
                biographyTextView.setVisibility(View.GONE);
            }

        }
    };
    private void checkUser(){
        try {
            if (user.getLogin() == null || user == null) {
                Toast.makeText(getActivity(), "This is not GitHub user link", Toast.LENGTH_SHORT).show();
                getActivity().finish();
                return;
            }
        }
        catch (NullPointerException e){
            Toast.makeText(getActivity(), "This is not GitHub user link", Toast.LENGTH_SHORT).show();
            getActivity().finish();
            return;
        }
    }
}
