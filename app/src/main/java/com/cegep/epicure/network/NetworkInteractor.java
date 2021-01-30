package com.cegep.epicure.network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkInteractor {

    private static EpicureService epicureService;

    private NetworkInteractor() {
        //no-op constructor
    }

    public static EpicureService getService() {
        if (epicureService == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://f1920d7dc135.ngrok.io/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(new OkHttpClient.Builder().addInterceptor(logging).build())
                    .build();
            epicureService = retrofit.create(EpicureService.class);
        }

        return epicureService;
    }
}
