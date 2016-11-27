package com.example.bogda.geekhubandroidgrouplist.service;

import com.example.bogda.geekhubandroidgrouplist.data.GooglePlusUser.GooglePlusUser;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by bogda on 26.11.2016.
 */

public interface GoogleApi {
    @GET("plus/v1/people/{username}?")
    Call<GooglePlusUser> getUser(@Path("username") String username, @Query("key") String key);

}
