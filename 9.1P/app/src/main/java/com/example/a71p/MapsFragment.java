package com.example.a71p;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a71p.database.LostFoundDatabaseHelper;
import com.example.a71p.model.LostFoundItem;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapsFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap map;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        return view;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;


        LostFoundDatabaseHelper dbHelper = new LostFoundDatabaseHelper(requireContext());
        List<LostFoundItem> items = dbHelper.getAllItems();

        for (LostFoundItem item : items) {
            try {
                String location = item.getLocation();
                Log.d("MapsFragment", "Parsing location: " + location);

                String[] parts = location.split(",");
                double lat = Double.parseDouble(parts[0].trim());
                double lng = Double.parseDouble(parts[1].trim());

                LatLng latLng = new LatLng(lat, lng);
                map.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title(item.getType() + ": " + item.getDescription()));
            } catch (Exception e) {
                Log.e("MapsFragment", "Error parsing location for item: " + item.getDescription(), e);
            }
        }

        if (!items.isEmpty()) {
            try {
                String[] first = items.get(0).getLocation().split(",");
                LatLng firstLatLng = new LatLng(Double.parseDouble(first[0].trim()), Double.parseDouble(first[1].trim()));
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(firstLatLng, 12));
            } catch (Exception e) {
                Log.e("MapsFragment", "Error centering map", e);
            }
        }
    }
}