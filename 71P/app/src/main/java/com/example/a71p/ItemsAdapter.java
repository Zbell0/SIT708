package com.example.a71p;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.example.a71p.database.LostFoundDatabaseHelper;
import com.example.a71p.model.LostFoundItem;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemViewHolder> {

    private List<LostFoundItem> items;
    private LostFoundDatabaseHelper dbHelper;

    public ItemsAdapter(List<LostFoundItem> items, LostFoundDatabaseHelper dbHelper) {
        this.items = items;
        this.dbHelper = dbHelper;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView textType, textDescription, textDate, textLocation;
        Button btnRemove;

        public ItemViewHolder(View itemView) {
            super(itemView);
            textType = itemView.findViewById(R.id.textType);
            textDescription = itemView.findViewById(R.id.textDescription);
            textDate = itemView.findViewById(R.id.textDate);
            textLocation = itemView.findViewById(R.id.textLocation);
            btnRemove = itemView.findViewById(R.id.btnRemove);
        }
    }

    @Override
    public ItemsAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_row, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        LostFoundItem item = items.get(position);
        holder.textType.setText(  item.getType() );
        if(item.getType().equalsIgnoreCase("Lost")){
            holder.textType.setTextColor(Color.RED);
        }else {
            holder.textType.setTextColor(Color.BLUE);
        }
        holder.textDescription.setText(item.getDescription());
        holder.textDate.setText("Date: " + item.getDate());
        holder.textLocation.setText("Location: " + item.getLocation());

        holder.btnRemove.setOnClickListener(v -> {
            dbHelper.deleteItem(item.getId());
            items.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, items.size());
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}