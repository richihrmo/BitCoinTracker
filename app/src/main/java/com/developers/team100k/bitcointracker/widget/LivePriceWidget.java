package com.developers.team100k.bitcointracker.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.Toast;
import com.developers.team100k.bitcointracker.R;

/**
 * Created by Richard Hrmo.
 */

public class LivePriceWidget extends AppWidgetProvider {

  private String name = "None";
  private String value = "0";
  private String change1h = "0";
  private String change24h = "0";
  private String change7d = "0";


  @Override
  public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
    for (int appWidgetId : appWidgetIds){
      Toast.makeText(context, String.valueOf(appWidgetIds.length), Toast.LENGTH_SHORT).show();
      updateWidget(context, appWidgetManager, appWidgetId);
    }
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

    Uri upImage = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
        "://" + context.getResources().getResourcePackageName(R.drawable.up)
        + '/' + context.getResources().getResourceTypeName(R.drawable.up) + '/' + context.getResources().getResourceEntryName(R.drawable.up) );
    Uri downImage = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
        "://" + context.getResources().getResourcePackageName(R.drawable.down)
        + '/' + context.getResources().getResourceTypeName(R.drawable.down) + '/' + context.getResources().getResourceEntryName(R.drawable.down) );

    if (intent.getExtras() != null){
      name = intent.getExtras().getString("name", "NaN");
//      String[] strings = intent.getExtras().getStringArray("name_more");
//      if (strings == null) Toast.makeText(context, "Null", Toast.LENGTH_SHORT).show();
      value = intent.getExtras().getString("value", "0");
      change1h = intent.getExtras().getString("change1h", "0");
      change24h = intent.getExtras().getString("change24h", "0");
      change7d = intent.getExtras().getString("change7d", "0");
    }
    RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
    views.setTextViewText(R.id.currency_value, value);
    views.setTextViewText(R.id.currency_name, name);
    views.setTextViewText(R.id.text1h, "1h " + change1h + "%");
    views.setTextViewText(R.id.text24h, "24h " + change24h + "%");
    views.setTextViewText(R.id.text7d, "7d " + change7d + "%");
    if (change1h.matches("[-]\\d*[.]*\\d*")) views.setImageViewUri(R.id.image1h, downImage);
    else views.setImageViewUri(R.id.image1h, upImage);
    if (change24h.matches("[-]\\d*[.]*\\d*")) views.setImageViewUri(R.id.image24h, downImage);
    else views.setImageViewUri(R.id.image24h, upImage);
    if (change7d.matches("[-]\\d*[.]*\\d*")) views.setImageViewUri(R.id.image7d, downImage);
    else views.setImageViewUri(R.id.image7d, upImage);
    ComponentName appWidget = new ComponentName(context, LivePriceWidget.class);
    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
    appWidgetManager.updateAppWidget(appWidget, views);
  }
}
