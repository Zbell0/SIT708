package com.example.a61d;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Intent incoming = getIntent();
        Uri uri = incoming != null ? incoming.getData() : null;

        if (uri != null
                && "myquiz".equals(uri.getScheme())
                && "profile".equals(uri.getHost())) {

            String user = uri.getLastPathSegment();
            launchFragment(ProfileFragment.newInstance(user));
            return;
        }


        if (savedInstanceState == null) {
            launchFragment(new LoginFragment());
        }
    }


    private void launchFragment(Fragment f) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, f)
                .commit();
    }
}