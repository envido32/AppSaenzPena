package com.electiva.envido32.appsaenzpena;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class PadronActivity extends AppCompatActivity {

    public Toolbar myToolbar;
    public WebView myWebView;
    int dni;
    int distrito;
    int sexo; // 0 = male, 1 = female;
    private ProgressBar progressBar;
    private static final long WAIT_JAVA_DELAY = 500;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_padron);

        myToolbar = (Toolbar) findViewById(R.id.appbar);
        myToolbar.setTitle(R.string.padron);
        setSupportActionBar(myToolbar);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        SharedPreferences config;
        config = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        //distrito = config.getInt("opcDistrito", -1);

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
            if(progressBar!=null){
                progressBar.setVisibility(View.INVISIBLE);  //To Hide ProgressBar

            }
        }
    }



    // Agregar botones al Toolbar
    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        //if(usrType==1) {
        if(true) { // DEBUG
            getMenuInflater().inflate(R.menu.main, menu);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    // Configurar botones del toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.action_settings: {
                //Creamos el Intent
                Intent intent =
                        new Intent(getBaseContext(), SettingsActivity.class);

                //Iniciamos la nueva actividad
                startActivity(intent);
                return true;
            }

            case R.id.action_add: {

                myWebView.setVisibility(View.GONE);
                IntentIntegrator integrator = new IntentIntegrator(this);
                integrator.setPrompt("Lea el codigo de barras al frente del DNI");
                integrator.setOrientationLocked(true);

        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
//        integrator.setCameraId(0);  // Use a specific camera of the device
        integrator.setBeepEnabled(true);
        integrator.setBarcodeImageEnabled(true);
        integrator.initiateScan();
                return true;
            }

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                myWebView.setVisibility(View.GONE);
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                myWebView.setVisibility(View.VISIBLE);
                String barcode = result.getContents();
                // Should Read: 00000000000@APELLIDO@NOMBRE@M@99999999@01/01/1900@01/01/1900
                String[] info=barcode.split("@");
                /*
                for(int i=0; i<info.length; i++){
                    Toast.makeText(this, "Valor: " +i+ " - "+info[i], Toast.LENGTH_LONG).show();
                }
                Toast.makeText(this, "Readed: "+pdfdnicode, Toast.LENGTH_LONG).show();
                */
                String orden = info[0];
                String apellido = info[1];
                String nombre = info[2];
                Toast.makeText(this, "Gracias " + nombre + " " + apellido, Toast.LENGTH_LONG).show();
                //String sexo = info[3];
                switch (info[3]) {
                    case "M": sexo = 0; break;
                    case "F": sexo = 1; break;
                    default:
                }
                //String dni = info[4];
                dni = Integer.parseInt(info[4]);
                String emicion = info[5];
                String vencimiento = info[6];

                SharedPreferences prefs;
                prefs = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                //distrito = Integer.parseInt(prefs.getString("opcDistrito", "-1"));
                distrito = prefs.getInt("opcDistrito", -1);

                String js = "javascript:" +
                        "document.getElementById('matricula').value='"+dni+"';" +
                        "document.getElementById('distrito').value='"+distrito+"';" +
                        "document.getElementsByName('sexo')['"+sexo+"'].checked=true;";

                myWebView.evaluateJavascript(js, new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String s) {
                    }
                });
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
