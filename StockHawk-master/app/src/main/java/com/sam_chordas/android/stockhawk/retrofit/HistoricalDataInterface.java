package com.sam_chordas.android.stockhawk.retrofit;

import retrofit2.Call;
import retrofit2.http.*;
import retrofit2.http.Query;

/**
 * Created by Suj🌠 on 17-12-2016.
 */

public interface HistoricalDataInterface {
    @GET("/v1/public/yql?&format=json&diagnostics=true&env=store://datatables.org/alltableswithkeys&callback=")
    Call<HistoricalDataResponse> GET_HISTORICAL_DATA( @Query("q") String stockSymbol);
}
