package com.duodevloopers.foodup.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.duodevloopers.foodup.Constant.Constant;
import com.duodevloopers.foodup.R;
import com.duodevloopers.foodup.databinding.ActivityMapBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.vmadalin.easypermissions.EasyPermissions;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import static android.content.ContentValues.TAG;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, EasyPermissions.PermissionCallbacks {

    GoogleMap mGoogleMap;
    boolean isPermissionGranted;
    private FusedLocationProviderClient mLocationClient;
    private ActivityMapBinding binding;
    private Location mLocation;
    private LatLng myLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mLocationClient = LocationServices.getFusedLocationProviderClient(this);


        if (hasLocationPermission()) {
            isPermissionGranted = true;
        } else {
            requestLocationPermission();
        }

        if (isPermissionGranted) {
            if (isGPSenable()) {
                mapFragment.getMapAsync(this);
                getCurrentLocation();
            }
        }

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCurrentLocation();
            }
        });

    }


    private boolean isGPSenable() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean providerEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (providerEnable) {
            return true;
        } else {
            new AlertDialog.Builder(this).setTitle("GPS Permission")
                    .setMessage("Please Enable GPS")
                    .setPositiveButton("OK", ((dialogInterface, i) -> {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(intent, Constant.GPS_REQUEST_CODE);
                    })).setCancelable(true).show();
        }
        return false;
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        mLocationClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            mLocation = location;
//                            myLocation = new LatLng(mLocation.getLatitude(), mLocation.getLongitude());
//                            getAddressFromLocation(mLocation.getLatitude(), mLocation.getLongitude());
                            gotoLocation(22.496600331944418, 91.72107610220237);
                        } else {
                            Log.d(TAG, "onSuccess: null");
                        }
                    }
                });
    }

    private void gotoLocation(double latitude, double longitude) {
        LatLng latLng = new LatLng(latitude, longitude);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 17);
        mGoogleMap.moveCamera(cameraUpdate);
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//        mGoogleMap.addMarker(new MarkerOptions().position(latLng)
//                .icon(BitmapFromVector(getApplicationContext(), R.drawable.ic_location))
//                .title("My Location"));
    }

    //.icon(BitmapFromVector(getApplicationContext(), R.drawable.ic_location))

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(@NonNull @NotNull GoogleMap googleMap) {
        mGoogleMap = googleMap;
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
//        mGoogleMap.setMyLocationEnabled(true);

    }

    @Override
    protected void onPause() {
        super.onPause();
        //To Do
    }

    private boolean hasLocationPermission() {
        return EasyPermissions.hasPermissions(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
    }

    private void requestLocationPermission() {
        EasyPermissions.requestPermissions(
                this,
                "This app requires location permission",
                Constant.PERMISSION_LOCATION_REQUEST_CODE,
                Manifest.permission.ACCESS_FINE_LOCATION
        );
    }


    @Override
    public void onPermissionsDenied(int i, @NotNull List<String> list) {
        if (EasyPermissions.somePermissionDenied(this, list.get(0))) {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", getPackageName(), "");
            intent.setData(uri);
            startActivity(intent);
        } else {
            requestLocationPermission();
        }
    }

    @Override
    public void onPermissionsGranted(int i, @NotNull List<String> list) {
        isPermissionGranted = true;
        Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
    }

    private BitmapDescriptor BitmapFromVector(Context context, int vectorResId) {
        // below line is use to generate a drawable.
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);

        // below line is use to set bounds to our vector drawable.
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());

        // below line is use to create a bitmap for our
        // drawable which we have added.
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);

        // below line is use to add bitmap in our canvas.
        Canvas canvas = new Canvas(bitmap);

        // below line is use to draw our
        // vector drawable in canvas.
        vectorDrawable.draw(canvas);

        // after generating our bitmap we are returning our bitmap.
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }


}