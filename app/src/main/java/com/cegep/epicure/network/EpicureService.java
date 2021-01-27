package com.cegep.epicure.network;

import com.cegep.epicure.model.Recipe;
import com.cegep.epicure.model.SignUpRequest;
import com.cegep.epicure.model.User;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface EpicureService {

    @GET("Users")
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    Call<List<User>> fetchUsersList();

    @POST("Recipes")
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    Call<Recipe> createRecipe(@Body Recipe recipe);

    @POST("Users")
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    Call<User> createUser(@Body SignUpRequest signUpRequest);
}
