package com.electiva.envido32.appsaenzpena;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class DistritoActivity extends AppCompatActivity
        implements GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks {

    private TextView txtDetect;
    private ImageButton imageButton;
    private Spinner spinner;
    public SharedPreferences prefs;
    private static final String LOGTAG = "android-localizacion";
    private static final int PETICION_PERMISO_LOCALIZACION = 101;
    private GoogleApiClient apiClient;
    private double lat;
    private double lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distrito);

        imageButton=(ImageButton) findViewById(R.id.imgbtn_gps);
        txtDetect=(TextView) findViewById(R.id.text_gps);
        spinner=(Spinner) findViewById(R.id.spinner_distrito);
        prefs = getSharedPreferences("MyPref", Context.MODE_PRIVATE);

        int prevpos = prefs.getInt("opcDistrito", -1);
        //int dist = Integer.parseInt(getResources().getStringArray(R.array.list_values_distritos)[prevposq]);
        //parent.getItemAtPosition(position));

        int[] aux = getResources().getIntArray(R.array.list_values_distritos);
        for(int i=0; i < aux.length; i++){
            if(aux[i]==prevpos){
                prevpos=i;
            }
        }
        spinner.setSelection(prevpos);

        apiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();

        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent,
                                               android.view.View v, int position, long id) {
                        txtDetect.setText("Seleccionado: " +
                                parent.getItemAtPosition(position));

                        SharedPreferences prefs =
                                getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();

                        //String dist = getResources().getStringArray(R.array.list_values_distritos)[position];
                        //int dist = Integer.parseInt(getResources().getStringArray(R.array.list_values_distritos)[position]);
                        int dist = getResources().getIntArray(R.array.list_values_distritos)[position];
                        //String dist = parent.getItemAtPosition(position).toString();
                        editor.putInt("opcDistrito", dist);
                        //editor.commit();
                        editor.apply();

                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                        txtDetect.setText("");
                    }
                });

        imageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
                    List<Address> addresses = gcd.getFromLocation(lat, lng, 1);

                    if (addresses.size() > 0) {

                        String countryName = addresses.get(0).getAdminArea();
                        if (countryName.equals("Buenos Aires")) {
                            String cp = addresses.get(0).getPostalCode();
                            if (cp.charAt(0) == 'C') {
                                countryName = "Capital Federal";
                            }
                        }
                        Toast.makeText(getApplicationContext(), "Distrito: " + countryName, Toast.LENGTH_LONG).show();
                        txtDetect.setText("Detectado: " + countryName);

                        int pos = -1;
                        String[] aux = getResources().getStringArray(R.array.list_titles_distritos);
                        for(int i=0; i < aux.length; i++){
                            if(aux[i].equals(countryName)){
                                pos=i;
                            }
                        }
                        spinner.setSelection(pos);

                        SharedPreferences prefs =
                                getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        int dist = getResources().getIntArray(R.array.list_values_distritos)[pos];
                        editor.putInt("opcDistrito", dist);
                        //editor.commit();
                        editor.apply();
                    }
                }catch (IOException ex){
                    //Log.e(LOGTAG, "Se ha interrumpido la conexión con Google Play Services");
                }
            }
        });
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        //Conectado correctamente a Google Play Services

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PETICION_PERMISO_LOCALIZACION);
        } else {

            Location lastLocation =
                    LocationServices.FusedLocationApi.getLastLocation(apiClient);
            lat = lastLocation.getLatitude();
            lng = lastLocation.getLongitude();

            updateUI(lastLocation);
        }
    }

    private void updateUI(Location loc) {
        String s="Latitud: (desconocida) \n " +
                "Longitud: (desconocida)";
        if (loc != null) {
            s="Latitud: " + String.valueOf(loc.getLatitude()) + "\n" +
            "Longitud: " + String.valueOf(loc.getLongitude());
        }
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        //Se ha producido un error que no se puede resolver automáticamente
        //y la conexión con los Google Play Services no se ha establecido.

        Log.e(LOGTAG, "Error grave al conectar con Google Play Services");
    }

    @Override
    public void onConnectionSuspended(int i) {
        //Se ha interrumpido la conexión con Google Play Services

        Log.e(LOGTAG, "Se ha interrumpido la conexión con Google Play Services");
    }
}