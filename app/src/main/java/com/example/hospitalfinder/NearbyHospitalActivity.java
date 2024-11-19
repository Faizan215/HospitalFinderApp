package com.example.hospitalfinder;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;

public class NearbyHospitalActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final int FINE_LOCATION_PERMISSION_REQUEST_CODE = 1;
    private static final float DEFAULT_ZOOM_LEVEL = 14f;

    private GoogleMap mMap;
    private Location currentLocation;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private PlacesClient placesClient;

    private static final LatLng[] HOSPITAL_LOCATIONS = {
            new LatLng(12.9345, 77.6100), // Manipal Hospital
            new LatLng(12.9362, 77.6228), // Fortis Hospital
            new LatLng(12.9279, 77.6277), // Apollo Hospital
            new LatLng(12.9325, 77.6123), // Columbia Asia Hospital
            new LatLng(12.9082, 77.6519), // Sakra World Hospital
            new LatLng(12.9732, 77.5912), // Narayana Health
            new LatLng(12.9766, 77.6010), // Mallya Hospital
            new LatLng(12.9440, 77.5676), // St. John's Medical College
            new LatLng(12.9602, 77.5977), // Vikram Hospital
            new LatLng(12.9218, 77.6174), // Sagar Hospitals
            new LatLng(12.9565, 77.7010), // Cloudnine Hospital
            new LatLng(12.9101, 77.6467), // NH Health City
            new LatLng(12.9853, 77.5801), // Sparsh Hospital
            new LatLng(12.9171, 77.6288), // Aster RV Hospital
            new LatLng(12.9807, 77.6936), // Motherhood Hospital
            new LatLng(13.0973, 77.6149), // Rajankunte Hospital
            new LatLng(12.9153, 77.5941), // Cloud9 Hospital JP Nagar
            new LatLng(12.9005, 77.5886), // Aster CMI Hospital JP Nagar
            new LatLng(12.9351, 77.6242), // Sakra Premium Hospital
            new LatLng(12.9150, 77.5865), // Rajeshwari Medical College Hospital
            new LatLng(12.9261, 77.6762), // Hosmat Hospital
            new LatLng(12.9310, 77.6203), // Bhagwan Mahaveer Jain Hospital
            new LatLng(12.9569, 77.5853), // Victoria Hospital
            new LatLng(12.9618, 77.6384), // Sparsh Super Specialty Hospital
            new LatLng(12.9189, 77.6417), // People Tree Hospitals
            new LatLng(12.9342, 77.6094), // Rainbow Children's Hospital
            new LatLng(12.9607, 77.5428), // NIMHANS
            new LatLng(12.9272, 77.6194), // Chinmaya Mission Hospital
            new LatLng(12.9473, 77.5975), // Agadi Hospital
            new LatLng(12.9594, 77.6026), // Bowring and Lady Curzon Hospital
            new LatLng(12.9414, 77.5915), // Kidwai Cancer Institute
            new LatLng(12.9740, 77.6033), // Vydehi Institute of Medical Sciences
            new LatLng(12.9374, 77.5905), // MS Ramaiah Memorial Hospital
            new LatLng(12.9657, 77.6096), // Mallige Hospital
            new LatLng(12.9268, 77.6823), // Shankara Cancer Hospital
            new LatLng(12.9598, 77.5413), // St. Martha's Hospital
            new LatLng(12.9185, 77.5646), // KIMS Hospital
            new LatLng(12.9338, 77.6153), // Lotus Multispeciality Hospital
            new LatLng(12.9676, 77.5923), // ESI Hospital
            new LatLng(12.9276, 77.6369), // BGS Gleneagles Global Hospital
            new LatLng(13.1094, 77.6189), // Raksha Health Care
            new LatLng(12.9110, 77.5900), // Chiguru
            new LatLng(13.136720, 77.534568) // Rajankunte
    };

    private static final String[] HOSPITAL_NAMES = {
            "Manipal Hospital",
            "Fortis Hospital",
            "Apollo Hospital",
            "Columbia Asia Hospital",
            "Sakra World Hospital",
            "Narayana Health",
            "Mallya Hospital",
            "St. John's Medical College",
            "Vikram Hospital",
            "Sagar Hospitals",
            "Cloudnine Hospital",
            "NH Health City",
            "Sparsh Hospital",
            "Aster RV Hospital",
            "Motherhood Hospital",
            "Rajankunte Hospital",
            "Cloud9 Hospital JP Nagar",
            "Aster CMI Hospital JP Nagar",
            "Sakra Premium Hospital",
            "Rajeshwari Medical College Hospital",
            "Hosmat Hospital",
            "Bhagwan Mahaveer Jain Hospital",
            "Victoria Hospital",
            "Sparsh Super Specialty Hospital",
            "People Tree Hospitals",
            "Rainbow Children's Hospital",
            "NIMHANS",
            "Chinmaya Mission Hospital",
            "Agadi Hospital",
            "Bowring and Lady Curzon Hospital",
            "Kidwai Cancer Institute",
            "Vydehi Institute of Medical Sciences",
            "MS Ramaiah Memorial Hospital",
            "Mallige Hospital",
            "Shankara Cancer Hospital",
            "St. Martha's Hospital",
            "KIMS Hospital",
            "Lotus Multispeciality Hospital",
            "ESI Hospital",
            "BGS Gleneagles Global Hospital",
            "Raksha Health Care",
            "Chiguru Hospital",
            "Rajankunte"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_hospital);

        // Initialize the Places API
        Places.initialize(getApplicationContext(), "AIzaSyDHte8-d47YnQgbuU4X4w7wpf8uWjkL67w");
        placesClient = Places.createClient(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchHospital(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            getLastLocation();
        }
    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }

        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(location -> {
                    if (location != null) {
                        currentLocation = location;
                        LatLng currentLatLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, DEFAULT_ZOOM_LEVEL));
                        Toast.makeText(this, "Current location updated", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Unable to fetch current location", Toast.LENGTH_SHORT).show();
                        LatLng defaultLatLng = new LatLng(12.9716, 77.5946); // Default: Bangalore
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLatLng, DEFAULT_ZOOM_LEVEL));
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to get location: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }

        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);

        addHospitalMarkers();
    }

    private void addHospitalMarkers() {
        for (int i = 0; i < HOSPITAL_LOCATIONS.length; i++) {
            mMap.addMarker(new MarkerOptions()
                    .position(HOSPITAL_LOCATIONS[i])
                    .title(HOSPITAL_NAMES[i]));
        }
    }

    private void searchHospital(String query) {
        for (int i = 0; i < HOSPITAL_NAMES.length; i++) {
            if (HOSPITAL_NAMES[i].toLowerCase().contains(query.toLowerCase())) {
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(HOSPITAL_LOCATIONS[i], DEFAULT_ZOOM_LEVEL));
                return;
            }
        }
        Toast.makeText(this, "Hospital not found", Toast.LENGTH_SHORT).show();
    }
}
