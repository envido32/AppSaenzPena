package com.electiva.envido32.appsaenzpena;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class PadronActivity extends AppCompatActivity {

    public Toolbar myToolbar;
    public WebView myWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_padron);

        myToolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(myToolbar);

        myWebView = (WebView) findViewById(R.id.webview);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.loadUrl("https://www.padron.gob.ar/");
    }
    // Agregar botones al Toolbar
    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return true;
    }

}
