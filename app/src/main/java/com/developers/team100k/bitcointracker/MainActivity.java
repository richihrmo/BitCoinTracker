package com.developers.team100k.bitcointracker;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import com.developers.team100k.bitcointracker.adapter.ListViewAdapter;
import com.developers.team100k.bitcointracker.jsonData.Currency;
import com.developers.team100k.bitcointracker.jsonData.JsonParser;
import java.util.List;

/**
 * Created by Richard Hrmo.
 */

public class MainActivity extends AppCompatActivity {

  private JsonParser jsonParser;
  private List<Currency> currencies;

  private Toolbar toolbar;
  private ListView listView;
  private ListViewAdapter adapter;
  private FloatingActionButton button;

  private static int i = 0;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    toolbar.setTitle(R.string.app_name);
    toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

    listView = findViewById(R.id.list_view);
    listView.setOnItemClickListener((adapterView, view, i, l) -> {
      if (listView.isClickable()){
        if (view.isSelected()) {
          view.setSelected(false);
        }
        else view.setSelected(true);
        jsonParser.addWantedCurrency(currencies.get(i));
        adapter.notifyDataSetChanged();
      }
    });

    button = findViewById(R.id.action_button);
    button.setOnClickListener(view -> {
      if (i % 2 == 0){
        listView.setClickable(true);
        button.setImageResource(R.drawable.check);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        adapter.setList(jsonParser.getCurrency());
        adapter.notifyDataSetChanged();
        i++;
      } else {
        listView.setClickable(false);
        button.setImageResource(R.drawable.edit);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_NONE);
        adapter.setList(jsonParser.getCurrencyWanted());
        adapter.notifyDataSetChanged();
        i++;
      }
    });

    jsonParser = new JsonParser(this);

    Runnable runnable = () -> {
      currencies = jsonParser.getCurrency();
      adapter = new ListViewAdapter(MainActivity.this, jsonParser.getCurrencyWanted());
      listView.setAdapter(adapter);
    };
    new Handler().postDelayed(runnable, 1000);
  }

  public void content(){
    jsonParser.jsonFromURL();
    Runnable runnable = () -> {
      currencies = jsonParser.getCurrency();
//      refresh();
    };
//    new Handler().postDelayed(runnable, 1000);
  }

  public void refresh(boolean wanted){
    if (wanted){
      adapter.setList(jsonParser.getCurrencyWanted());
      adapter.notifyDataSetChanged();
    } else {
      adapter.setList(jsonParser.getCurrency());
      adapter.notifyDataSetChanged();
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) { 
    MenuInflater menuInflater = getMenuInflater();
    menuInflater.inflate(R.menu.main_menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()){
      case R.id.refresh_button:
        //refresh
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }
}
