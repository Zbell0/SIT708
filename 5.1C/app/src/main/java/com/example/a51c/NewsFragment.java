package com.example.a51c;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView recyclerView_TopNews;
    private RecyclerViewAdapter adapter;
    private RecyclerViewAdapter_TopNews topNewsAdapter;
    private List<NewsItem> newsList;
    private Button goBackBtn;

    public NewsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_news, container, false);

        goBackBtn = view.findViewById(R.id.goBack);
        goBackBtn.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .remove(NewsFragment.this)
                    .commit();
            requireActivity().findViewById(R.id.news).setVisibility(view.VISIBLE);
            requireActivity().findViewById(R.id.iTube).setVisibility(view.VISIBLE);
        });

        // RecyclerView connection
        recyclerView = view.findViewById(R.id.newsRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setHasFixedSize(true);

        recyclerView_TopNews = view.findViewById(R.id.topStoriesRecyclerView);
        recyclerView_TopNews.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

        // Dummy data
        newsList = getDummyNews();

        // Adapter connection
        adapter = new RecyclerViewAdapter(getContext(), newsList);
        topNewsAdapter = new RecyclerViewAdapter_TopNews(getContext(),newsList);
        recyclerView.setAdapter(adapter);
        recyclerView_TopNews.setAdapter(topNewsAdapter);
        return view;
    }


    private List<NewsItem> getDummyNews() {
        List<NewsItem> list = new ArrayList<>();
        list.add(new NewsItem("Ella's App Goes Global", "Trending in 30 countries!", R.drawable.news1));
        list.add(new NewsItem("Android 15 Preview", "What's new this year?", R.drawable.news1));
        list.add(new NewsItem("Women in Tech", "Breaking barriers in software", R.drawable.news1));
        list.add(new NewsItem("iTube Launch", "Stream your playlist easily", R.drawable.news1));
        return list;
    }

}