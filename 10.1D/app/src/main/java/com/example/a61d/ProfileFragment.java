package com.example.a61d;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;


public class ProfileFragment extends Fragment {

    /* ---- constants ---- */
    private static final String ARG_USERNAME  = "username";
    private static final String PROFILE_URL   =
            "http://10.0.2.2:5001/profileStats?username=";
    private static final String SHARE_BASE    =
            "http://10.0.2.2:5001/share/profile/";


    private String username = "anonymous";


    public static ProfileFragment newInstance(String username) {
        ProfileFragment f = new ProfileFragment();
        Bundle b = new Bundle();
        b.putString(ARG_USERNAME, username);
        f.setArguments(b);
        return f;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        /* view refs */
        TextView nameTv    = v.findViewById(R.id.profileName);
        TextView totalTv   = v.findViewById(R.id.totalQ);
        TextView correctTv = v.findViewById(R.id.correctQ);
        TextView wrongTv   = v.findViewById(R.id.wrongQ);
        Button   shareBtn  = v.findViewById(R.id.shareBtn);
        Button   homeBtn   = v.findViewById(R.id.homeBtn);
        Button   upgradeBtn= v.findViewById(R.id.upgradeBtn);

        /* args */
        if (getArguments() != null) {
            username = getArguments().getString(ARG_USERNAME, "anonymous");
        }
        nameTv.setText(username);

        /* 1) fetch stats */
        RequestQueue q = Volley.newRequestQueue(requireContext());
        JsonObjectRequest req = new JsonObjectRequest(
                Request.Method.GET,
                PROFILE_URL + username,
                null,
                res -> {
                    int total   = res.optInt("total",   0);
                    int correct = res.optInt("correct", 0);
                    int wrong   = res.optInt("wrong",   0);

                    totalTv.setText(String.valueOf(total));
                    correctTv.setText(String.valueOf(correct));
                    wrongTv.setText(String.valueOf(wrong));
                },
                err -> {
                    err.printStackTrace();
                    Toast.makeText(getContext(),
                            "Failed to load profile stats", Toast.LENGTH_SHORT).show();
                });
        q.add(req);

        /* 2) share */
        shareBtn.setOnClickListener(vv -> {
            String link = SHARE_BASE + username;
            String msg  = link;

            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("text/plain");
            share.putExtra(Intent.EXTRA_TEXT, msg);
            startActivity(Intent.createChooser(share, "Share via"));
        });

        /* 3) go home */
        homeBtn.setOnClickListener(vv ->
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container,
                                HomeFragment.newInstance(username))
                        .commit());

        /* 4) upgrade */
        upgradeBtn.setOnClickListener(vv ->
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container,
                                new UpgradeFragment())   // or UpgradeFragment.newInstance(...)
                        .addToBackStack(null)
                        .commit());

        return v;
    }
}