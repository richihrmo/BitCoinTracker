package com.developers.team100k.bitcointracker.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by Richard Hrmo.
 */

public class LivePriceWidgetService extends RemoteViewsService {
  @Override
  public RemoteViewsFactory onGetViewFactory(Intent intent) {
    return new LivePriceWidgetDataProvider(this, intent);
  }
}
