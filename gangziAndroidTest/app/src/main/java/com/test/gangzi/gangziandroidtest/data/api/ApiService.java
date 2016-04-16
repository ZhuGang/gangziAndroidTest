package com.test.gangzi.gangziandroidtest.data.api;

import com.test.gangzi.gangziandroidtest.model.User;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by gagnzi on 2015/6/10.
 */
public interface ApiService {

    @GET("/users")
    public void getUsers(Callback<List<User>> callback);
}
