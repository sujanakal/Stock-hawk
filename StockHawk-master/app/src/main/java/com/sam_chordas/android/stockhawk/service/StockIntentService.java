package com.sam_chordas.android.stockhawk.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;
import com.google.android.gms.gcm.TaskParams;

/**
 * Created by sam_chordas on 10/1/15.
 */
public class StockIntentService extends IntentService {
  public int result;
  public int RESULT_CODE = 0;
  public final String RESULT_STATUS = "resultStatus";
  final String RESULT_RECEIVER = "ResultReceiver";
  final String TAG = "tag";
  final String ADD = "add";
  final String SYMBOL = "symbol";

  public StockIntentService(){
    super(StockIntentService.class.getName());
  }

  public StockIntentService(String name) {
    super(name);
  }

  @Override protected void onHandleIntent(Intent intent) {
    ResultReceiver resultReceiver = intent.getParcelableExtra(RESULT_RECEIVER);
    Log.d(StockIntentService.class.getSimpleName(), "Stock Intent Service");
    StockTaskService stockTaskService = new StockTaskService(this);
    Bundle args = new Bundle();
    if (intent.getStringExtra(TAG).equals(ADD)){
      args.putString(SYMBOL, intent.getStringExtra(SYMBOL));
    }
    // We can call OnRunTask from the intent service to force it to run immediately instead of
    // scheduling a task.
    result = stockTaskService.onRunTask(new TaskParams(intent.getStringExtra(TAG), args));
    args.putInt(RESULT_STATUS,result);
    resultReceiver.send(RESULT_CODE,args);
  }
}
