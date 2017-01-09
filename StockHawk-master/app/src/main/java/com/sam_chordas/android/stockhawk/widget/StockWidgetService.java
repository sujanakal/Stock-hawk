package com.sam_chordas.android.stockhawk.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by Suj🌠 on 27-12-2016.
 */

public class StockWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        // return remote views factory
        return new StockWidgetRemoteViewsFactory(this,intent);
    }
}
