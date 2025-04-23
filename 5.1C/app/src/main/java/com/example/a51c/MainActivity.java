package com.example.a51c;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.a51c.NewsFragment;
import com.example.a51c.R;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void SelectFragment(View view) {

        findViewById(R.id.news).setVisibility(View.GONE);
        findViewById(R.id.iTube).setVisibility(View.GONE);
        findViewById(R.id.fragment_container).setVisibility(View.VISIBLE);

        Fragment fragmentToShow;

        if (view.getId() == R.id.news) {
            fragmentToShow = new NewsFragment();
        } else if (view.getId() == R.id.iTube) {
            fragmentToShow = new LoginFragment();
        } else {
            fragmentToShow = new NewsFragment();
        }


        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragmentToShow)
                .addToBackStack(null)
                .commit();
    }
        public void showButtons() {
            findViewById(R.id.news).setVisibility(View.VISIBLE);
            findViewById(R.id.iTube).setVisibility(View.VISIBLE);
            findViewById(R.id.fragment_container).setVisibility(View.GONE);
    }
}