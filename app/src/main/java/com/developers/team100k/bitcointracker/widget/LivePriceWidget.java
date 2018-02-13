package com.developers.team100k.bitcointracker.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.Toast;
import com.developers.team100k.bitcointracker.R;

/**
 * Created by Richard Hrmo.
 */

public class LivePriceWidget extends AppWidgetProvider {
  @Override
  public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

    final int n = appWidgetIds.length;

    Toast.makeText(context, String.valueOf(appWidgetIds.length), Toast.LENGTH_SHORT).show();

    for (int i = 0; i < n; i++){
      int appWidgetId = appWidgetIds[i];

      RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
      remoteViews.setRemoteAdapter(appWidgetId, new Intent(context, LivePriceWidgetService.class));

      Intent intent = new Intent(context, LivePriceWidget.class);
      intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
      intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
      PendingIntent pendingIntent = PendingIntent.getBroadcast(
          context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

      remoteViews.setOnClickPendingIntent(R.id.refresh_button, pendingIntent);
      appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }
  }
}
