package com.developers.team100k.bitcointracker.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.developers.team100k.bitcointracker.jsonData.Currency;
import com.developers.team100k.bitcointracker.R;
import io.realm.CurrencyRealmProxy;
import java.util.List;

/**
 * Created by Richard Hrmo.
 */

public class ListViewAdapter extends BaseAdapter {

  private Context context;
  private List<Currency> mList;
  private List<Currency> lastList;

  public ListViewAdapter(Context context, List<Currency> list){
    this.context = context;
    this.mList = list;
  }

  @Override
  public int getCount() {
    return mList.size();
  }

  @Override
  public Currency getItem(int position) {
    return mList.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    if (lastList != null && lastList.contains(mList.get(position))){
      //TODO
    }
    ViewHolderItem viewHolder;

    if (convertView == null) {
      convertView = LayoutInflater.from(context).inflate(R.layout.listview_layout, parent, false);

      viewHolder = new ViewHolderItem();
      viewHolder.name = convertView.findViewById(R.id.currency_name);
      viewHolder.value = convertView.findViewById(R.id.currency_value);
      viewHolder.image = convertView.findViewById(R.id.imageView);
      viewHolder.checkbox = convertView.findViewById(R.id.checkbox);

      convertView.setTag(viewHolder);
    } else {
      viewHolder = (ViewHolderItem) convertView.getTag();
    }

    Currency currency = getItem(position);
    if (currency != null){
      viewHolder.name.setText(currency.getName());
      viewHolder.value.setText(currency.getPrice_usd());
      viewHolder.image.setImageResource(context.getResources().getIdentifier(currency.getSymbol().toLowerCase(), "drawable", context.getPackageName()));
      if (currency.isSelected()) viewHolder.checkbox.setImageResource(R.drawable.check);
      else viewHolder.checkbox.setImageResource(R.drawable.edit);
    }

    return convertView;
  }

  public void setList(List<Currency> list) {
    lastList = mList;
    mList = list;
  }

}
