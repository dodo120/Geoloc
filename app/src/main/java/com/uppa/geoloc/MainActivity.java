package com.uppa.geoloc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;

import java.nio.charset.StandardCharsets;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView tvLocation;
    private Button buttonLocation;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION};
        ActivityCompat.requestPermissions(this,permissions,1);

        tvLocation = findViewById(R.id.tvLocalisation);
        buttonLocation = findViewById(R.id.btnLocation);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        buttonLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    fusedLocationClient.getLastLocation()
                            .addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
                                @Override
                                public void onSuccess(Location location) {

                                    if (location != null) {

                                        String response = "("+location.getLatitude()+";"+location.getLongitude()+")";
                                        Log.d("LOCATION",response);

                                        Retrofit retrofit = new Retrofit.Builder()
                                                .addConverterFactory(GsonConverterFactory.create())
                                                .baseUrl("https://api-adresse.data.gouv.fr/")
                                                .build();

                                        LocationAPI service = retrofit.create(LocationAPI.class);
                                        Call<GeoResponse> locationCall = service.getAdresse(location.getLongitude(),location.getLatitude());
                                        locationCall.enqueue(new Callback<GeoResponse>() {
                                            @Override
                                            public void onResponse(Call<GeoResponse> call, Response<GeoResponse> response) {
                                                Log.d("JSON",response.toString());

                                                tvLocation.setText(response.body().getFeatures().get(0).getProperties().getLabel());
                                            }

                                            @Override
                                            public void onFailure(Call<GeoResponse> call, Throwable t) {

                                            }
                                        });

                                    }
                                }
                            });
                    return;
                }

            }
        });

    }

}