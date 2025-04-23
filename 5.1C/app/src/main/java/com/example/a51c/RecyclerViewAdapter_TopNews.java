package com.example.a51c;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter_TopNews extends RecyclerView.Adapter<RecyclerViewAdapter_TopNews.ViewHolder> {
    public RecyclerViewAdapter_TopNews(Context context, List<NewsItem> newsItem) {
        this.context = context;
        NewsItem = newsItem;
    }

    private List<NewsItem> NewsItem;
    private Context context;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.top_news_item,parent,false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.topNewsImage.setImageResource(NewsItem.get(position).getImageResId());
        holder.itemView.setOnClickListener(v -> {
            NewsItem selectedNews = NewsItem.get(holder.getAdapterPosition());

            Fragment detailFragment = NewsDetailFragment.newInstance(
                    selectedNews.getImageResId(),
                    selectedNews.getTitle(),
                    selectedNews.getDescription()
            );

            FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, detailFragment)
                    .addToBackStack(null)
                    .commit();
        });



    }

    @Override
    public int getItemCount() {
        return NewsItem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView topNewsImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            topNewsImage= itemView.findViewById(R.id.topNewsImage);


        }
    }
}
