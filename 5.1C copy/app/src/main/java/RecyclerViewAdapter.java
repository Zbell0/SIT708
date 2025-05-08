import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a51c.R;

import org.w3c.dom.Text;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    public RecyclerViewAdapter(Context context, List<NewsItem> newsItem) {
        this.context = context;
        NewsItem = newsItem;
    }

    private List<NewsItem> NewsItem;
    private Context context;
    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.news_item,parent,false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        holder.newsTitle.setText(NewsItem.get(position).getTitle());
        holder.newsDescription.setText(NewsItem.get(position).getDescription());
        holder.newsImage.setImageResource(NewsItem.get(position).getImageResId());

    }

    @Override
    public int getItemCount() {
        return NewsItem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView newsImage;
        TextView newsTitle;
        TextView newsDescription;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            newsImage= itemView.findViewById(R.id.newsImage);
            newsTitle = itemView.findViewById(R.id.newsTitle);
            newsDescription= itemView.findViewById(R.id.newsDescription);

        }
    }
}
