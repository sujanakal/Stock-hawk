package com.sam_chordas.android.stockhawk.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Suj🌠 on 17-12-2016.
 */

public class Quote {
    @SerializedName("Symbol")
    @Expose
    public String Symbol;

    @SerializedName("Date")
    @Expose
    public String Date;

    @SerializedName("Open")
    @Expose
    public String OpenH;

    @SerializedName("High")
    @Expose
    public String High;

    @SerializedName("Low")
    @Expose
    public String Low;

    @SerializedName("Close")
    @Expose
    public String Close;

    @SerializedName("Volume")
    @Expose
    public String Volume;

    @SerializedName("Adj_Close")
    @Expose
    public String Adj_Close;
}
