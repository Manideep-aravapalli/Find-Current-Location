package com.example.googlemaps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Locale;

public class MapsActivity2 extends AppCompatActivity implements LocationListener {

  LocationManager locationManager;
  Geocoder geocoder;
  Button button;
  TextView textView;
  List<Address> addresses;
  FusedLocationProviderClient fusedLocationProviderClient;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_maps2);

    button = (Button) findViewById(R.id.find);
    textView = (TextView) findViewById(R.id.address2);
    geocoder = new Geocoder(this, Locale.getDefault());

    button.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            getLastLocation();
          }
        });
  }

  private void getLastLocation() {
    try {
      locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
      if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
              != PackageManager.PERMISSION_GRANTED
          && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
              != PackageManager.PERMISSION_GRANTED) {
        return;
      }
      locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, this);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void onLocationChanged(Location location) {
    textView.setText(
        "Current Location: " + location.getLatitude() + ", " + location.getLongitude());
  }

  @Override
  public void onStatusChanged(String provider, int status, Bundle extras) {
    Toast.makeText(this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
  }

  @Override
  public void onProviderEnabled(String provider) {}

  @Override
  public void onProviderDisabled(String provider) {}
}
