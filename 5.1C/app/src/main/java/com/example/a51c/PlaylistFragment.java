package com.example.a51c;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.Set;

public class PlaylistFragment extends Fragment {

    public PlaylistFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_playlist, container, false);
        LinearLayout containerLayout = view.findViewById(R.id.playlistContainer);

        SharedPreferences prefs = requireActivity().getSharedPreferences("playlist", Context.MODE_PRIVATE);
        Set<String> urls = prefs.getStringSet("urls", null);

        if (urls == null || urls.isEmpty()) {
            TextView emptyMsg = new TextView(getContext());
            emptyMsg.setText("No videos in playlist.");
            containerLayout.addView(emptyMsg);
        } else {
            for (String url : urls) {
                TextView videoView = new TextView(getContext());
                videoView.setText(url);
                videoView.setPadding(0, 24, 0, 24);
                videoView.setTextSize(16);
                videoView.setOnClickListener(v -> {
                    String videoId = extractVideoId(url);
                    if (videoId != null) {
                        PlayerFragment playerFragment = PlayerFragment.newInstance(videoId);
                        requireActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, playerFragment)
                                .addToBackStack(null)
                                .commit();
                    } else {
                        Toast.makeText(getContext(), "Invalid URL", Toast.LENGTH_SHORT).show();
                    }
                });
                containerLayout.addView(videoView);
            }
        }
        // Go Back button handler
        Button btnGoBack = view.findViewById(R.id.goBack);
        btnGoBack.setOnClickListener(v -> {
            Log.d("goBack", "Go back button clicked!");
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        return view;
    }

    private String extractVideoId(String url) {
        if (url.contains("watch?v=")) {
            String[] parts = url.split("watch\\?v=");
            if (parts.length > 1) {
                String id = parts[1];
                int ampIndex = id.indexOf("&");
                return ampIndex != -1 ? id.substring(0, ampIndex) : id;
            }
        }
        return null;
    }
}