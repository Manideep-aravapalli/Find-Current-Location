package com.example.googlemaps;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

  private GoogleMap mMap;
  TextView textView, add_line, add_city, add_state, add_country, add_postalCode, add_knownName;
  Location mLocation;
  Geocoder geocoder;
  List<Address> addresses;
  FusedLocationProviderClient fusedLocationProviderClient;
  private static final int Request_Code = 101;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_maps);

    geocoder = new Geocoder(this, Locale.getDefault());

    textView = (TextView) findViewById(R.id.address);
    add_line = (TextView) findViewById(R.id.address_line);
    add_city = (TextView) findViewById(R.id.city);
    add_state = (TextView) findViewById(R.id.state);
    add_country = (TextView) findViewById(R.id.country);
    add_postalCode = (TextView) findViewById(R.id.postalCode);
    add_knownName = (TextView) findViewById(R.id.knownName);
    fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
    getLastLocation();

    /*  // Obtain the SupportMapFragment and get notified when the map is ready to be used.
    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
            .findFragmentById(R.id.map);
    mapFragment.getMapAsync(this);*/
  }

  private void getLastLocation() {

    Task<Location> task = fusedLocationProviderClient.getLastLocation();
    task.addOnSuccessListener(new OnSuccessListener<Location>() {
          @Override
          public void onSuccess(Location location) {
            if (location != null) {
              mLocation = location;
              Toast.makeText(
                      getApplicationContext(),
                      location.getLatitude() + " " + location.getLongitude(),
                      Toast.LENGTH_LONG)
                  .show();
              textView.setText(
                  "Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());
              SupportMapFragment mapFragment =
                  (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
              mapFragment.getMapAsync(MapsActivity.this);

              try {
                addresses =
                    geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                String address =
                    addresses
                        .get(0)
                        .getAddressLine(
                            0); // If any additional address line present than only, check with max
                                // available address lines by getMaxAddressLineIndex()
               /* String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country   = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName =
                    addresses.get(0).getFeatureName(); // Only if available else return NULL
*/
                add_line.setText(address);
               /* add_city.setText(city);
                add_country.setText(country);
                add_state.setText(state);
                add_postalCode.setText(postalCode);
                add_knownName.setText(knownName);*/
              } catch (IOException e) {
                e.printStackTrace();
              }
            }
          }
        });
  }

  @Override
  public void onMapReady(GoogleMap googleMap) {
    mMap = googleMap;

    // Add a marker in Sydney and move the camera
    LatLng latLng = new LatLng(mLocation.getLatitude(), mLocation.getLongitude());
    mMap.addMarker(new MarkerOptions().position(latLng).title("Marker is Here"));
    mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 7));
  }

  @Override
  public void onRequestPermissionsResult(
      int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    switch (requestCode) {
      case Request_Code:
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          getLastLocation();
        }
        break;
    }
  }
}
