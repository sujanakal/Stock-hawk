package com.sam_chordas.android.stockhawk.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Suj🌠 on 17-12-2016.
 */

public class HistoricalDataResponse {
    @SerializedName("query")
    @Expose
    public Query query;
}
