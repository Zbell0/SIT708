package com.example.a51c;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class NewsDetailFragment extends Fragment {

    private static final String ARG_IMAGE = "image";
    private static final String ARG_TITLE = "title";
    private static final String ARG_CONTENT = "content";

    private int imageRes;
    private String title;
    private String content;

    public NewsDetailFragment() {
        // Required empty public constructor
    }
    private Button goBack_newsDetail;

    public static NewsDetailFragment newInstance(int imageRes, String title, String content) {
        NewsDetailFragment fragment = new NewsDetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_IMAGE, imageRes);
        args.putString(ARG_TITLE, title);
        args.putString(ARG_CONTENT, content);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            imageRes = getArguments().getInt(ARG_IMAGE);
            title = getArguments().getString(ARG_TITLE);
            content = getArguments().getString(ARG_CONTENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_detail, container, false);

        goBack_newsDetail = view.findViewById(R.id.goBack_newsDetail);
        goBack_newsDetail.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        ImageView detailImage = view.findViewById(R.id.detailImage);
        TextView detailTitle = view.findViewById(R.id.detailTitle);
        TextView detailContent = view.findViewById(R.id.detailContent);

        detailImage.setImageResource(imageRes);
        detailTitle.setText(title);
        detailContent.setText(content);

        return view;
    }
}