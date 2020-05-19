package com.example.cou;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class privacyPolicy extends AppCompatActivity {

    WebView web;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);

        web = findViewById(R.id.webView);

        String url= "https://discountscouponswebsite.blogspot.com/2020/01/privacy-policy.html"; //Website link
        web.setWebViewClient(new WebViewClient());
        web.loadUrl(url);

        WebSettings webSettings = web.getSettings();
        webSettings.setJavaScriptEnabled(true);

    }
}
