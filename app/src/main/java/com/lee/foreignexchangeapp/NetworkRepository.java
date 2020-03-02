package com.lee.foreignexchangeapp;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

 class NetworkRepository {
    final private MutableLiveData<CurrencyModel> currencyData = new MutableLiveData<>();
    private CompositeDisposable disposable = new CompositeDisposable();
    private String TAG = NetworkRepository.class.getSimpleName();

    private void getCurrencyInfo() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        disposable.add(
                apiService.getCurrencyInfo(Constant.API_KEY, Constant.SYMBOLS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<CurrencyModel>() {
                            @Override
                            public void onSuccess(CurrencyModel currency) {
                                Log.i(TAG, "network info:" + currency.toString());
                                currencyData.setValue(currency);
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e(TAG, "onError: " + e.getMessage());
                            }
                        })
        );
    }

     MutableLiveData<CurrencyModel> obtainCurrencyInfo() {
        getCurrencyInfo();
        return currencyData;
    }
}


