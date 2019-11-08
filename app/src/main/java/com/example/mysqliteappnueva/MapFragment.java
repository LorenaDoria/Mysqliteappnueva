package com.example.mysqliteappnueva;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {
    private View rootView;
    private MapView mapView;
    private GoogleMap Map;
    private Double lat;
    private Double lng;


    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_map, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView = rootView.findViewById(R.id.map);
        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Map = googleMap;
        LatLng location = lat_lng();
        LatLng place = new LatLng(123123,3123123);

        CameraUpdate zoom = CameraUpdateFactory.zoomTo(12);
        Map.addMarker(new MarkerOptions().position(place).title("Address"));
        //gMap.addMarker(new MarkerOptions().position(location).snippet(address).title("Address"));
        Map.moveCamera(CameraUpdateFactory.newLatLng(place));
        Map.animateCamera(zoom);

    }

    private LatLng lat_lng() {
        Bundle b = this.getArguments();
        if (b != null) {
            lat = b.getDouble("lat", 8.7502488);
            lng = b.getDouble("lng", -75.88324639999999);
        }
        LatLng location = new LatLng(lat, lng);

        return location;
    }
}
