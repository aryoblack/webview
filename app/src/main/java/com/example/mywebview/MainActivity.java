package com.example.mywebview;

import android.graphics.Bitmap;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

// create BY ARYO
import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class MainActivity extends AppCompatActivity {
    WebView webView;
    WebView view;
    SwipeRefreshLayout mySwipeRefreshLayout;
    ProgressBar bar;
    //URL web, yang akan kita gunakan pada Webview
    private String website = "http://aryo-tutorial.blogspot.com/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mySwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        mySwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadWeb();
            }
        });

        LoadWeb();

    }


    public void LoadWeb(){
        webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.loadUrl("http://192.168.111.197/asset_it/");
        mySwipeRefreshLayout.setRefreshing(true);
        webView.setWebViewClient(new WebViewClient(){
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                webView.loadUrl("file:///android_asset/error.html");
            }
            public  void  onPageFinished(WebView view, String url){
                //ketika loading selesai, ison loading akan hilang
                mySwipeRefreshLayout.setRefreshing(false);
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                //loading akan jalan lagi ketika masuk link lain
                // dan akan berhenti saat loading selesai
                if(webView.getProgress()== 100){
                    mySwipeRefreshLayout.setRefreshing(false);
                } else {
                    mySwipeRefreshLayout.setRefreshing(true);
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if((keyCode==KeyEvent.KEYCODE_BACK) && webView.canGoBack()){
            webView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
