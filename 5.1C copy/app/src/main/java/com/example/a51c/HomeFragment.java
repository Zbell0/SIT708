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
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.HashSet;
import java.util.Set;

public class HomeFragment extends Fragment {

    private EditText etYoutubeUrl;
    private Button btnPlay, btnAddToPlaylist, btnMyPlaylist;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        etYoutubeUrl = view.findViewById(R.id.etYoutubeUrl);
        btnPlay = view.findViewById(R.id.btnPlay);
        btnAddToPlaylist = view.findViewById(R.id.btnAddToPlaylist);
        btnMyPlaylist = view.findViewById(R.id.btnMyPlaylist);

        btnPlay.setOnClickListener(v -> {
            String url = etYoutubeUrl.getText().toString().trim();
            if (url.isEmpty()) {
                Toast.makeText(getContext(), "Please enter a YouTube URL", Toast.LENGTH_SHORT).show();
            } else {
                String videoId = extractVideoId(url);
                if (videoId != null) {

                    PlayerFragment playerFragment = PlayerFragment.newInstance(videoId );
                    requireActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, playerFragment)
                            .addToBackStack(null)
                            .commit();
                } else {
                    Toast.makeText(getContext(), "Invalid YouTube URL", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnAddToPlaylist.setOnClickListener(v -> {
            String url = etYoutubeUrl.getText().toString().trim();
            if (!url.isEmpty()) {
                SharedPreferences prefs = requireActivity().getSharedPreferences("playlist", Context.MODE_PRIVATE);
                Set<String> playlist = prefs.getStringSet("urls", new HashSet<>());
                playlist.add(url);
                prefs.edit().putStringSet("urls", playlist).apply();
                Toast.makeText(getContext(), "Video added to playlist!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Enter a video URL first", Toast.LENGTH_SHORT).show();
            }
        });

        btnMyPlaylist.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new PlaylistFragment())
                    .addToBackStack(null)
                    .commit();
        });
        // Go Back button handler
        Button btnGoBack = view.findViewById(R.id.goBack);
        btnGoBack.setOnClickListener(v -> {
            Log.d("goBack", "Go back button clicked!");
            requireActivity().getSupportFragmentManager().popBackStack();
            ((MainActivity) requireActivity()).showButtons();

        });


        return view;
    }

    // Simple extraction (only works with watch?v= format)
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