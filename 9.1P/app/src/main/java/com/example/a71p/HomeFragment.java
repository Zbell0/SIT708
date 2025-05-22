package com.example.a71p;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Button createAdvertButton = view.findViewById(R.id.btn_create_advert);
        Button viewItemsButton = view.findViewById(R.id.btn_view_items);
        Button showMapButton = view.findViewById(R.id.btn_show_map);  // ← 지도 버튼 추가

        createAdvertButton.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new CreateAdvertFragment())
                    .addToBackStack(null)
                    .commit();
        });

        viewItemsButton.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new ItemsListFragment())
                    .addToBackStack(null)
                    .commit();
        });


        showMapButton.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new MapsFragment())
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }
}