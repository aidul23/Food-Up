package com.duodevloopers.foodup;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.internal.IGoogleMapDelegate;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class MapFragment extends Fragment {

    GoogleMap map;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false);

//        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
//
//        //Async map
//        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
//            @Override
//            public void onMapReady(final GoogleMap googleMap) {
//                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//                    @Override
//                    public void onMapClick(LatLng latLng) {
//                        LatLng Chittagong = new LatLng(22.3569, 91.7832);
//                        MarkerOptions markerOptions = new MarkerOptions();
//                        markerOptions.position(Chittagong);
//                        markerOptions.title("Chittagong");
//                        googleMap.clear();
//                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Chittagong, 10));
//                        googleMap.addMarker(markerOptions);
//                    }
//                });
//            }
//        });
//        return view;
    }
}