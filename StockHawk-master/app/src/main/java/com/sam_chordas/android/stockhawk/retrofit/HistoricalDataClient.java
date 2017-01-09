package com.sam_chordas.android.stockhawk.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Suj🌠 on 17-12-2016.
 */

public class HistoricalDataClient {
    public static Retrofit getClient(){
        final String API_BASE_URL = "https://query.yahooapis.com";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}
