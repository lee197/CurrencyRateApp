package com.lee.foreignexchangeapp;

import androidx.lifecycle.ViewModelProvider;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputLayout;

public class MainFragment extends Fragment {
    private CurrencyUpdate currencyViewModel;
    static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currencyViewModel = new ViewModelProvider(this).get(CurrencyViewModel.class);

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView usdView = view.findViewById(R.id.usd_value);
        TextView audView = view.findViewById(R.id.aud_value);
        TextView cadView = view.findViewById(R.id.cad_value);
        TextView chfView = view.findViewById(R.id.chf_value);
        TextView cnyView = view.findViewById(R.id.cny_value);
        TextView gdpView = view.findViewById(R.id.gbp_value);
        TextView jpyView = view.findViewById(R.id.jpy_value);
        TextView nzdView = view.findViewById(R.id.nzd_value);
        TextView sekView = view.findViewById(R.id.sek_value);

        currencyViewModel.getCurrencyInfo().observe(this.getViewLifecycleOwner(), currencyInfo -> {

            // could optimize with data binding
            usdView.setText(String.valueOf(currencyInfo.getRates(Constant.USD)));
            audView.setText(String.valueOf(currencyInfo.getRates(Constant.AUD)));
            cadView.setText(String.valueOf(currencyInfo.getRates(Constant.CAD)));
            chfView.setText(String.valueOf(currencyInfo.getRates(Constant.CHF)));
            cnyView.setText(String.valueOf(currencyInfo.getRates(Constant.CNY)));
            gdpView.setText(String.valueOf(currencyInfo.getRates(Constant.GBP)));
            jpyView.setText(String.valueOf(currencyInfo.getRates(Constant.JPY)));
            nzdView.setText(String.valueOf(currencyInfo.getRates(Constant.NZD)));
            sekView.setText(String.valueOf(currencyInfo.getRates(Constant.SEK)));
        });

        TextInputLayout eurInputLayout = view.findViewById(R.id.eur_input);
        Button confirmButton = view.findViewById(R.id.confirm_button);
        Button resetButton = view.findViewById(R.id.reset_button);

        confirmButton.setOnClickListener(v -> {
            String inputString = eurInputLayout.getEditText().getText().toString();

            if(!inputString.equals("")){
                Double amount = Double.valueOf(inputString.trim());
                currencyViewModel.updateEurAmount(amount);
                eurInputLayout.getEditText().getText().clear();

                // keyboard operation
                InputMethodManager imm =(InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

            }else{
                Toast.makeText(this.getContext(), "Please input a number", Toast.LENGTH_LONG).show();
            }
        });

        resetButton.setOnClickListener(v -> currencyViewModel.resetCurrency());
    }
}
