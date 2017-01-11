package com.sam_chordas.android.stockhawk.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.data.QuoteColumns;
import com.sam_chordas.android.stockhawk.data.QuoteProvider;
import com.sam_chordas.android.stockhawk.rest.Stock;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Suj🌠 on 27-12-2016.
 */

public class StockWidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory{
    List<String> widgetData = new ArrayList<>(); //example

    ArrayList<Stock> stockList;
    Context context;
    Intent intent;
    Cursor cursor = null;

    public StockWidgetRemoteViewsFactory(Context context, Intent intent) {
        this.context = context;
        this.intent = intent;
    }

    public void initData(){

        stockList = new ArrayList<Stock>();
        stockList.clear();
        final String[] QUOTE_COLUMN = {
                QuoteColumns.SYMBOL,
                QuoteColumns.BIDPRICE,
                QuoteColumns.CHANGE,
                QuoteColumns.ISUP
        };

        // example
        widgetData.clear();
        for(int i=0; i<10; i++){
            widgetData.add("List item " + i);
        }

        //actual
        cursor = context.getContentResolver().query(QuoteProvider.Quotes.CONTENT_URI,QUOTE_COLUMN,null,null,null);
        Log.d("WIDGET_FACTORY: ",cursor.toString());

        try{
            for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
                stockList.add(new Stock(cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        Boolean.valueOf(cursor.getString(3))
                ));
            }
        }finally {
            cursor.close();
        }
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {

        Thread thread = new Thread() {
            public void run() {
                query();
            }
        };
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            Log.d("WIDGET_FACTORY", "Thread interrupted !");
        }
    }

    private void query() {
        initData();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return stockList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_list_item_quote);

        remoteViews.setTextViewText(R.id.stock_symbol,stockList.get(position).getSymbol());
        remoteViews.setTextViewText(R.id.bid_price,stockList.get(position).getBidPrice());
        remoteViews.setTextViewText(R.id.change,stockList.get(position).getChange());

        Intent fillIntent = new Intent();
        remoteViews.setOnClickFillInIntent(R.id.widget_list_item,fillIntent);

        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
