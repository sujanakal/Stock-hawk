package com.sam_chordas.android.stockhawk.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Suj🌠 on 17-12-2016.
 */

public class Results {
    @SerializedName("quote")
    @Expose
    public List<Quote> quote = new ArrayList<Quote>();
}
