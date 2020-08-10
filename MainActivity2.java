package com.example.griview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity2 extends AppCompatActivity {

   private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        String Value = getIntent().getStringExtra("link");
        webView = findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(Value);


        WebSettings webSettings=webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }
    @Override
    public void onBackPressed() {
        if(webView.canGoBack()) {
            webView.goBack();
        }
        else{
            super.onBackPressed();
        }
    }
}
