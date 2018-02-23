package com.developers.team100k.bitcointracker.jsonData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import io.realm.RealmObject;

/**
 * Created by Richard Hrmo.
 */

public class Currency extends RealmObject{
  @SerializedName("id")
  @Expose
  private String id;
  @SerializedName("name")
  @Expose
  private String name;
  @SerializedName("symbol")
  @Expose
  private String symbol;
  @SerializedName("rank")
  @Expose
  private String rank;
  @SerializedName("price_usd")
  @Expose
  private String price_usd;
  @SerializedName("price_btc")
  @Expose
  private String price_btc;
  @SerializedName("24h_volume_usd")
  @Expose
  private String volume_usd;
  @SerializedName("market_cap_usd")
  @Expose
  private String market_cap_usd;
  @SerializedName("available_supply")
  @Expose
  private String available_supply;
  @SerializedName("total_supply")
  @Expose
  private String total_supply;
  @SerializedName("max_supply")
  @Expose
  private String max_supply;
  @SerializedName("percent_change_1h")
  @Expose
  private String percent_change_1h;
  @SerializedName("percent_change_24h")
  @Expose
  private String percent_change_24h;
  @SerializedName("percent_change_7d")
  @Expose
  private String percent_change_7d;
  @SerializedName("last_updated")
  @Expose
  private String last_updated;
  private boolean selected;

  public Currency(){}

  public Currency(String name, String price_usd){
    this.name = name;
    this.price_btc = price_usd;
  }

  public String getName() {
    return name;
  }

  public String getPrice_usd() {
    return price_usd;
  }

  public String getSymbol() {
    return symbol;
  }

  public String getPercent_change_1h() {
    return percent_change_1h;
  }

  public String getPercent_change_24h() {
    return percent_change_24h;
  }

  public String getPercent_change_7d() {
    return percent_change_7d;
  }

  public boolean isSelected() {
    return selected;
  }

  public void setSelected(boolean selected) {
    this.selected = selected;
  }
}