package com.developers.team100k.bitcointracker.jsonData;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.developers.team100k.bitcointracker.widget.LivePriceWidget;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

/**
 * Created by Richard Hrmo.
 */

public class JsonParser {

  private Context mContext;
  private static final String old_url = "https://api.coinmarketcap.com/v1/ticker/bitcoin";
  private static final String new_url = "https://api.coinmarketcap.com/v1/ticker/?limit=10";
  private static final String url = "https://api.coinmarketcap.com/v1/ticker";
  private String json;

  private static String filename = "tempData";

  private List<Currency> mCurrency = new ArrayList<>();
  private List<Currency> mCurrencyWanted = new ArrayList<>();
  private RequestQueue queue;
//  private Realm mRealm = Realm.getDefaultInstance();

  public JsonParser(Context context){
    this.mContext = context;
    new Timer().schedule(new TimerTask() {
      @Override
      public void run() {
        jsonFromURL();
      }
    }, 0 , 60000);
  }

  /**
   * Convert JSON data to Java Collection using GSON
   * @param json json
   */
  private void jsonToCollection(String json){
    Type type = new TypeToken<List<Currency>>(){}.getType();
//    Gson gson = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
//      @Override
//      public boolean shouldSkipField(FieldAttributes f) {
//        return f.getDeclaredClass().equals(RealmObject.class);
//      }
//
//      @Override
//      public boolean shouldSkipClass(Class<?> clazz) {
//        return false;
//      }
//    }).create();
    Gson gson = new Gson();
    mCurrency = gson.fromJson(json, type);
//    writeToRealm();
  }

  /**
   * Get JSON data from API URL
   */
  public void jsonFromURL(){
    Log.e("RANDOM", "jsonFromURL call");
    if (isOnline()){
      if (queue == null) queue = Volley.newRequestQueue(mContext);
      StringRequest stringRequest = new StringRequest(url, response -> {
        Log.e("RANDOM", "response from server");
        json = response;
        jsonToCollection(json);
        writeToFile(mContext);
        updateWidget(mCurrency.get(0));
      }, error -> Toast.makeText(mContext, "Could not reach server", Toast.LENGTH_SHORT).show());
      queue.add(stringRequest);
    } else {
      Log.e("RANDOM", "not online");
      if (mCurrency.isEmpty()){
        json = readFile(mContext, filename);
        jsonToCollection(json);
      }
    }
  }

//  public void writeToRealm(){
//    mRealm.beginTransaction();
//    mRealm.copyToRealm(mCurrency);
//    mRealm.commitTransaction();
//  }

//  private void writeToSharedPreferences(){
//
//  }

  /**
   *  write JSON data to file for offline access to data
   */
  private void writeToFile(Context context){
    FileOutputStream outputStream;
    try {
      outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
      outputStream.write(json.getBytes());
      outputStream.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   *  read JSON data from file for offline access to data when internet is not available
   */
  private String readFile(Context context, String filename) {
    try {
      FileInputStream fis = context.openFileInput(filename);
      InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
      BufferedReader bufferedReader = new BufferedReader(isr);
      StringBuilder sb = new StringBuilder();
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        sb.append(line).append("\n");
      }
      return sb.toString();
    } catch (IOException e) {
      return "";
    }
  }

  public void addWantedCurrency(Currency currency){
    if (mCurrencyWanted.contains(currency)) {
      mCurrencyWanted.remove(currency);
      currency.setSelected(false);
    } else {
      mCurrencyWanted.add(currency);
      currency.setSelected(true);
    }
  }

  /**
   * check whether there is Internet connection or not
   * @return boolean value
   */
  private boolean isOnline() {
    ConnectivityManager cm =
        (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo netInfo = null;
    if (cm != null) {
      netInfo = cm.getActiveNetworkInfo();
    }
    return netInfo != null && netInfo.isConnectedOrConnecting();
  }

  private void updateWidget(Currency currency){
    Intent intent = new Intent(mContext, LivePriceWidget.class);
    intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
// Use an array and EXTRA_APPWIDGET_IDS instead of AppWidgetManager.EXTRA_APPWIDGET_ID,
// since it seems the onUpdate() is only fired on that:
    int[] ids = AppWidgetManager.getInstance(mContext).getAppWidgetIds(new ComponentName(mContext, LivePriceWidget.class));
    intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
    intent.putExtra("name", currency.getName());
    intent.putExtra("value", currency.getPrice_usd());
    mContext.sendBroadcast(intent);
  }

  public List<Currency> getCurrency() {
    return mCurrency;
  }

  public List<Currency> getCurrencyWanted(){
    return mCurrencyWanted;
  }

}
