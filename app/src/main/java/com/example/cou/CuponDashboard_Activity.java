package com.example.cou;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class CuponDashboard_Activity extends AppCompatActivity {

    TextView DisCodeBtn, PromCodeBtn,CashBackKBtn,HotDealsBtn,FreeRidesBtn, FreeGiftBtn;

    private InterstitialAd interstitialAd;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cupon_dashboard_);

        //Admob
        MobileAds.initialize(this,"ca-app-pub-2699591493465867~5559600553");
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-2699591493465867/5191741371");
        interstitialAd.loadAd(new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build());

        ///
        DisCodeBtn = findViewById(R.id.DisCodeBtn);
        PromCodeBtn = findViewById(R.id.PromCodeBtn);
        CashBackKBtn = findViewById(R.id.CashBackKBtn);
        HotDealsBtn = findViewById(R.id.HotDealsBtn);
        FreeRidesBtn = findViewById(R.id.FreeRidesBtn);
        FreeGiftBtn = findViewById(R.id.FreeGiftBtn);

        interstitialAd.setAdListener(new AdListener()
        {
            @Override
            public void onAdClosed() {
                interstitialAd.loadAd(new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build());
                StartActivity();
            }
        });






        DisCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "discountcodes";
                if (interstitialAd.isLoaded()){
                    interstitialAd.show();
                }else{

                    StartActivity();
                }

            }
        });
        PromCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "promocodes";
                if (interstitialAd.isLoaded()){
                    interstitialAd.show();
                }else{

                    StartActivity();
                }

            }
        });
        CashBackKBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "cashback";
                if (interstitialAd.isLoaded()){
                    interstitialAd.show();
                }else{

                    StartActivity();
                }

            }
        });
        HotDealsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "hotdeals";
                if (interstitialAd.isLoaded()){
                    interstitialAd.show();
                }else{

                    StartActivity();
                }

            }
        });
        FreeRidesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "freerides";
                if (interstitialAd.isLoaded()){
                    interstitialAd.show();
                }else{

                    StartActivity();
                }
            }
        });
        FreeGiftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "freegifts";
                if (interstitialAd.isLoaded()){
                    interstitialAd.show();
                }else{

                    StartActivity();
                }
            }
        });




    }

    private void StartActivity()
    {
        Intent intent = new Intent(CuponDashboard_Activity.this, ShowCode.class);
        intent.putExtra("Ctype",type);
        startActivity(intent);
    }
}
