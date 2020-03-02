package com.lee.foreignexchangeapp;

import com.google.gson.annotations.SerializedName;
import java.util.Map;

public class CurrencyModel {

    @SerializedName("success")
    private boolean success;

    @SerializedName("timestamp")
    private long timestamp;

    @SerializedName("base")
    private String base;

    @SerializedName("date")
    private String date;

    @SerializedName("rates")
    private Map<String, Double> rates;


     Double getRates(String country){

         Double rate = this.rates.get(country);
         if (rate == null){
             return 0.0;
         }else{
             return rate;
         }
     }
     void setRates(Map<String, Double> value) { this.rates = value; }


    @Override
    public String toString() {
        return "CurrencyModel{" +
                "success=" + success +
                ", timestamp=" + timestamp +
                ", base='" + base + '\'' +
                ", date='" + date + '\'' +
                ", rates=" + rates +
                '}';
    }
}
