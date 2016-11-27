package com.example.bogda.geekhubandroidgrouplist.service;

import com.example.bogda.geekhubandroidgrouplist.data.GitHubUser;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by bogda on 26.11.2016.
 */

public interface GitHubApi {
    String apiKey = "AIzaSyDk23y7ndIvFdIWyTCbntt50Y8ZH-DCgoo";
    @GET("/users/{username}")
    Call<GitHubUser> getUser(@Path("username") String username);
}
