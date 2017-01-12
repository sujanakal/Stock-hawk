package com.sam_chordas.android.stockhawk.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.EntryXComparator;
import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.retrofit.HistoricalDataClient;
import com.sam_chordas.android.stockhawk.retrofit.HistoricalDataInterface;
import com.sam_chordas.android.stockhawk.retrofit.HistoricalDataResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Suj🌠 on 15-12-2016.
 */

public class MyStocksDetailActivity extends AppCompatActivity{
    public final String DETAIL_ACTIVITY = MyStocksDetailActivity.class.getSimpleName();
    public String stockSymbol;
    public String stockBidPrice;
    LineChart stockChart;
    Date currentDate;
    Calendar cal;
    SimpleDateFormat dateFormat;
    public String startDate;
    public String endDate;
    public String queryString;
    public final String AMBER = "#FFC107";
    TextView stock_created_text_view;
    TextView start_date_text_view;
    TextView end_date_text_view;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        stockSymbol = getIntent().getStringExtra("Stock_Symbol");
        stockBidPrice = getIntent().getStringExtra("Bid_price");

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);


        cal = Calendar.getInstance();
        currentDate = cal.getTime();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        endDate = dateFormat.format(currentDate);
        //cal.add(Calendar.YEAR,-1); //For a year interval
       // cal.add(Calendar.MONTH,-6); //For 6 months interval
        cal.add(Calendar.MONTH,-1); //For 1 month interval
        startDate = dateFormat.format(cal.getTime());

        //queryString = "select * from yahoo.finance.historicaldata where symbol = \"YHOO\" and startDate = \"2009-09-11\" and endDate = \"2010-03-10\"";
        queryString = "select * from yahoo.finance.historicaldata where symbol = '" + stockSymbol
                + "' and startDate = '" + startDate
                + "' and endDate = '" + endDate
                + "'";
        HistoricalDataInterface apiService = HistoricalDataClient.getClient().create(HistoricalDataInterface.class);
        Call<HistoricalDataResponse> call = apiService.GET_HISTORICAL_DATA(queryString);

        Log.d("RETROFIT_QUERY_URL",call.request().url().toString());
        Log.d(DETAIL_ACTIVITY,"query string: "+queryString);

        call.enqueue(new Callback<HistoricalDataResponse>() {
            @Override
            public void onResponse(Call<HistoricalDataResponse> call, Response<HistoricalDataResponse> response) {
                setContentView(R.layout.detail_activity_my_stocks);
                String created = response.body().query.created;

                stockChart = (LineChart) findViewById(R.id.historicDataChart);
                stock_created_text_view = (TextView) findViewById(R.id.bid_price_detail);
                start_date_text_view = (TextView) findViewById(R.id.start_date_detail);
                end_date_text_view = (TextView) findViewById(R.id.end_date_detail);

                stock_created_text_view.setText(created);
                start_date_text_view.setText(startDate);
                end_date_text_view.setText(endDate);

                List<Entry> chartEntries = new ArrayList<Entry>(); // Data to be plotted in the chart
                ArrayList<String> xVals = new ArrayList<String>(); // Holds the labels to be displayed on the x-axis of the chart

                if(response.isSuccess()){
                    for(int i=0; i< response.body().query.results.quote.size(); i++){
                        String stockClose = response.body().query.results.quote.get(i).Close;
                        chartEntries.add(new Entry(Float.valueOf(stockClose),i));

                        /*if(i==0)
                            xVals.add(startDate.toString());
                        else if(i==response.body().query.results.quote.size()-10)
                            xVals.add(endDate.toString());
                        else xVals.add("");*/

                        xVals.add(response.body().query.results.quote.get(i).Date);

                    }
                    Collections.sort(chartEntries,new EntryXComparator());

                   //Setting Y Axis
                    YAxis yLAxis = stockChart.getAxisLeft();
                    yLAxis.setTextColor(Color.BLACK);
                    yLAxis.setDrawLabels(true);
                    yLAxis.setTextSize(15f);

                    YAxis yRAxis = stockChart.getAxisRight();
                    yRAxis.setTextColor(Color.BLACK);
                    yRAxis.setTextSize(15f);

                    //Setting X Axis
                    XAxis xAxis = stockChart.getXAxis();
                    xAxis.setDrawLabels(true);
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xAxis.setTextSize(15f);
                    xAxis.setTextColor(Color.BLACK);
                    xAxis.setDrawAxisLine(true);
                    xAxis.setDrawGridLines(true);

                    LineDataSet lineDataSet = new LineDataSet(chartEntries,stockSymbol);
                    lineDataSet.setColor(Color.parseColor(AMBER));
                    lineDataSet.setCircleColor(Color.parseColor(AMBER));
                    lineDataSet.setCircleColorHole(Color.WHITE);
                    lineDataSet.setValueTextColor(Color.BLACK);
                    lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
                    lineDataSet.setDrawFilled(true);
                    lineDataSet.setCircleSize(3f);
                    lineDataSet.setCircleHoleRadius(1.5f);

                    ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
                    dataSets.add(lineDataSet);

                    Legend legend = stockChart.getLegend();
                    legend.setTextSize(20f);

                    Description desc = new Description();
                    desc.setText(" ");
                    LineData lineData = new LineData(dataSets);      //////////
                    stockChart.setData(lineData);
                    stockChart.setDrawBorders(true);
                    stockChart.setBorderColor(R.color.grey_600);
                    //stockChart.setBorderColor(R.color.blue_500);
                    stockChart.setDescription(desc);
                    stockChart.invalidate();
                }

                /*List<Entry> entries = new ArrayList<Entry>(); // Data to be plotted in the chart
                float[] xVal = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
                float[] yVal = {111,222,333,444,555,666,777,888,999,1110};
                for(int i=0; i<xVal.length; i++){
                    entries.add(new Entry(xVal[i],yVal[i]));
                }
                LineDataSet lineDataSet = new LineDataSet(entries,"Label");
                lineDataSet.setColor(Color.CYAN);
                lineDataSet.setValueTextColor(Color.BLACK);

                LineData lineData = new LineData(lineDataSet);
                stockChart.setData(lineData);
                stockChart.invalidate();*/

            }

            @Override
            public void onFailure(Call<HistoricalDataResponse> call, Throwable t) {

            }
        });
    }
}
