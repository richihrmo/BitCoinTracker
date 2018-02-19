package com.developers.team100k.bitcointracker.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.widget.RemoteViews;
import android.widget.Toast;
import com.developers.team100k.bitcointracker.R;
import com.developers.team100k.bitcointracker.jsonData.Currency;

/**
 * Created by Richard Hrmo.
 */

public class LivePriceWidget extends AppWidgetProvider {

  private String name = "-";
  private String value = "-";

  @Override
  public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
    for (int appWidgetId : appWidgetIds){
      Toast.makeText(context, String.valueOf(appWidgetIds.length), Toast.LENGTH_SHORT).show();
      updateWidget(context, appWidgetManager, appWidgetId);
    }
//    super.onUpdate(context, appWidgetManager, appWidgetIds);
  }

  private void updateWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId){
    RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

    Intent intent = new Intent(context, LivePriceWidget.class);
    intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
//    intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

    PendingIntent pendingIntent = PendingIntent.getBroadcast(
        context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

    remoteViews.setOnClickPendingIntent(R.id.refresh_button, pendingIntent);
    appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
  }

  @Override
  public void onReceive(Context context, Intent intent) {
    super.onReceive(context, intent);

//    SharedPreferences sharedPreferences =
//        context.getSharedPreferences(Resources.getSystem()
//            .getString(R.string.preference_file),Context.MODE_PRIVATE);
//
//    String name = sharedPreferences.getString("name", "NaN");
//    String value = sharedPreferences.getString("value", "NaN");
    if (intent.getExtras() != null){
      name = intent.getExtras().getString("name", "NaN");
      value = intent.getExtras().getString("value", "NaN");
    }
    RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
    views.setTextViewText(R.id.currency_value, value);
    views.setTextViewText(R.id.currency_name, name);
    ComponentName appWidget = new ComponentName(context, LivePriceWidget.class);
    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
    appWidgetManager.updateAppWidget(appWidget, views);
  }
}
