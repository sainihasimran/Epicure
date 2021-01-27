package com.cegep.epicure.network;

import com.cegep.epicure.model.Recipe;
import com.cegep.epicure.model.SignUpRequest;
import com.cegep.epicure.model.User;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface EpicureService {

    @GET("Users")
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    Call<List<User>> fetchUsersList();

    @POST("Users")
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    Call<User> createUser(@Body SignUpRequest signUpRequest);

    @GET("Recipes")
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    Call<List<Recipe>> fetchRecipes(@Header("cat") String category);
}
