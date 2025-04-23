package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<Staff> staffList;
    private Context context;

    public RecyclerViewAdapter(Context context, List<Staff> staffList) {
        this.context = context;
        this.staffList = staffList;
    }

    @NonNull
    @Override
//    새 카드를 하나 만드는 함수 새 줄을 보여줘야 하니까 새 카드 만들어줘 그럼 여기서 staff.row.xml 을 화면에 붙여서 카드 하나 꺼내줘 만든 카드에 실제 내용을 붙이는 단계
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.staff_row,parent,false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
holder.rowNameTextView.setText(staffList.get(position).getName());
holder.rowPositionTextView.setText(staffList.get(position).getPosition());
    }
//총 몇 명 직원 있어요 라고 RecyclerView가 물어보면, 직원 수만큼 카드를 만들어야 하니까 리스트 길이 알려줄게 라고 대답하는 함수야
    @Override
    public int getItemCount() {
        return staffList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView rowNameTextView;
        TextView rowPositionTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rowNameTextView=itemView.findViewById(R.id.rowNameTextView);
            rowPositionTextView=itemView.findViewById(R.id.rowPositionTextView2);
        }
    }
}
