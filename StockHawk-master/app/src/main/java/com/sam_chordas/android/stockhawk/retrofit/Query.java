package com.sam_chordas.android.stockhawk.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Suj🌠 on 17-12-2016.
 */

public class Query {
    @SerializedName("count")
    @Expose
    public Integer count;

    @SerializedName("created")
    @Expose
    public String created;

    @SerializedName("lang")
    @Expose
    public String lang;

    @SerializedName("results")
    @Expose
    public Results results;
}
