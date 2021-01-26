package com.cegep.epicure.network;

import com.cegep.epicure.model.User;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface EpicureService {

    @GET("Users")
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    Call<List<User>> fetchUsersList();
}
