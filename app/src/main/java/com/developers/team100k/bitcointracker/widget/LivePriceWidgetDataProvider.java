package com.developers.team100k.bitcointracker.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.developers.team100k.bitcointracker.R;
import com.developers.team100k.bitcointracker.jsonData.Currency;
import com.developers.team100k.bitcointracker.jsonData.JsonParser;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Richard Hrmo.
 */

public class LivePriceWidgetDataProvider implements RemoteViewsService.RemoteViewsFactory{

  private JsonParser mJsonParser;
  private List<Currency> mCurrencies = new ArrayList<>();
  private Context mContext;

  public LivePriceWidgetDataProvider(Context context, Intent intent){
    mContext = context;
  }

  @Override
  public void onCreate() {
    mCurrencies.clear();
    mCurrencies.add(new Currency("random", "1000"));
//    mJsonParser = new JsonParser(mContext);
//    Runnable runnable = new Runnable() {
//      @Override
//      public void run() {
//        mCurrencies = mJsonParser.getCurrency();
//      }
//    };
//    new Handler().postDelayed(runnable, 1000);
  }

  @Override
  public void onDataSetChanged() {
    mCurrencies.clear();
    mCurrencies.add(new Currency("random", "1000"));
  }

  @Override
  public void onDestroy() {

  }

  @Override
  public int getCount() {
    return mCurrencies.size();
  }

  @Override
  public RemoteViews getViewAt(int i) {
    RemoteViews view = new RemoteViews(mContext.getPackageName(), R.layout.widget_layout);

    view.setTextViewText(R.id.currency_name, mCurrencies.get(0).getName());
    view.setTextViewText(R.id.currency_value, mCurrencies.get(0).getPrice_usd());
    return view;
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
  public long getItemId(int i) {
    return i;
  }

  @Override
  public boolean hasStableIds() {
    return true;
  }
}
