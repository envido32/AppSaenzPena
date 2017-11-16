package com.electiva.envido32.appsaenzpena;

import android.graphics.Bitmap;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

public class PadronActivity extends AppCompatActivity {

    public Toolbar myToolbar;
    public WebView myWebView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_padron);

        myToolbar = (Toolbar) findViewById(R.id.appbar);
        myToolbar.setTitle(R.string.padron);
        setSupportActionBar(myToolbar);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        myWebView = (WebView) findViewById(R.id.webview);
        myWebView.setWebViewClient(new MyWebViewClient());
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.loadUrl("https://www.padron.gob.ar/");
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            if(progressBar!=null){
                progressBar.setVisibility(View.VISIBLE); // To Show ProgressBar
            }
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            Toast.makeText(PadronActivity.this, "Error de carga!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            int dni = 123456;
            int distrito = 4;
            int sexo = 1; // 0 = male, 1 = female;

            try {
                Thread.sleep(1000);
            } catch(InterruptedException e) {}

            String js = "javascript:" +
                    "document.getElementById('matricula').value='"+dni+"';" +
                    "document.getElementById('distrito').value='"+distrito+"';" +
                    "document.getElementsByName('sexo')['"+sexo+"'].checked=true;";
                view.evaluateJavascript(js, new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String s) {
                    }
                });


            if(progressBar!=null){
                progressBar.setVisibility(View.INVISIBLE);  //To Hide ProgressBar

            }
        }
    }

    // Agregar botones al Toolbar
    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
