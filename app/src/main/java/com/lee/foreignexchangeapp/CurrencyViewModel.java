package com.lee.foreignexchangeapp;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.HashMap;

interface CurrencyUpdate {
     void updateEurAmount(Double amount);
     void resetCurrency();
     LiveData<CurrencyModel> getCurrencyInfo();
}

public class CurrencyViewModel extends ViewModel implements CurrencyUpdate {
    private MutableLiveData<CurrencyModel> currencyLiveData;
    private NetworkRepository networkRepository;

    private void initCurrencyInfo(){
        networkRepository = new NetworkRepository();
        currencyLiveData = networkRepository.obtainCurrencyInfo();
    }

    @Override
    public LiveData<CurrencyModel> getCurrencyInfo() {
        initCurrencyInfo();
        if (currencyLiveData == null) {
            currencyLiveData = new MutableLiveData<>();
        }
        return currencyLiveData;
    }

    @Override
    public void updateEurAmount(Double amount) {
        currencyLiveData.setValue(convertRate(currencyLiveData.getValue(),amount));
    }

    @Override
    public void resetCurrency() {
        currencyLiveData =  networkRepository.obtainCurrencyInfo();
    }

    private CurrencyModel convertRate(CurrencyModel currencyData,double amount){

        if (currencyData != null){
            Double usd = currencyData.getRates(Constant.USD);
            Double aud = currencyData.getRates(Constant.AUD);
            Double cad = currencyData.getRates(Constant.CAD);
            Double gbp = currencyData.getRates(Constant.GBP);
            Double jpy = currencyData.getRates(Constant.JPY);
            Double chf = currencyData.getRates(Constant.CHF);
            Double cny = currencyData.getRates(Constant.CNY);
            Double sek = currencyData.getRates(Constant.SEK);
            Double nzd = currencyData.getRates(Constant.NZD);
            HashMap<String, Double> currencyMap = new HashMap<>();
            currencyMap.put(Constant.USD,usd*amount);
            currencyMap.put(Constant.AUD,aud*amount);
            currencyMap.put(Constant.CAD,cad*amount);
            currencyMap.put(Constant.GBP,gbp*amount);
            currencyMap.put(Constant.JPY,jpy*amount);
            currencyMap.put(Constant.CHF,chf*amount);
            currencyMap.put(Constant.CNY,cny*amount);
            currencyMap.put(Constant.SEK,sek*amount);
            currencyMap.put(Constant.NZD,nzd*amount);
            currencyData.setRates(currencyMap);
        }

        return currencyData;
    }
}
