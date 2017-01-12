package com.sam_chordas.android.stockhawk.widget;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.RemoteViews;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.ui.MyStocksActivity;

/**
 * Implementation of App Widget functionality.
 */
public class StockWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.stock_quote_widget);

        //views.removeAllViews(R.id.widget_list);
        // set remote adapter
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH){
            setStockRemoteAdapter(context,views);
        }
        else{
            setStockRemoteAdapterV11(context,views);
        }

        views.setEmptyView(R.id.widget_list, R.id.widget_empty_view);

        Intent intent = new Intent(context, MyStocksActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.widget_list,pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
        views.removeAllViews(R.id.widget_list);
    }

    @SuppressWarnings("deprecation")
    private static void setStockRemoteAdapterV11(Context context, RemoteViews views) {
        views.setRemoteAdapter(0,R.id.widget_list,new Intent(context,StockWidgetService.class));
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private static void setStockRemoteAdapter(Context context, RemoteViews views) {
        views.setRemoteAdapter(R.id.widget_list,new Intent(context,StockWidgetService.class));
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
        super.onUpdate(context,appWidgetManager,appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

