package com.example.googlemaps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

  int REQUEST_CODE_ASK_PERMISSIONS = 101;
  Button next, next2;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    locationPermission();
    //    onClickGoogleMaps();
    next = (Button) findViewById(R.id.location);
    next2 = (Button) findViewById(R.id.location2);

    next.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
            startActivity(intent);
          }
        });
    next2.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), MapsActivity2.class);
            startActivity(intent);
          }
        });
  }

  public void onClickGoogleMaps() {}

  public void locationPermission() {
    if (Build.VERSION.SDK_INT >= 23) {
      // Android 6 and later
      if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
          != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(
            this,
            new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
            REQUEST_CODE_ASK_PERMISSIONS);
      } else {
        // Permission has not been granted before
        ActivityCompat.requestPermissions(
            this,
            new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
            REQUEST_CODE_ASK_PERMISSIONS);
      }
    }
  }
}
