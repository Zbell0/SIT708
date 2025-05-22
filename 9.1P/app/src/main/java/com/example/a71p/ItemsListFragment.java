package com.example.a71p;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a71p.database.LostFoundDatabaseHelper;
import com.example.a71p.model.LostFoundItem;

import java.util.List;

public class ItemsListFragment extends Fragment {

    private RecyclerView recyclerView;
    private ItemsAdapter adapter;
    private LostFoundDatabaseHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_items_list, container, false);

        recyclerView = view.findViewById(R.id.itemsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        dbHelper = new LostFoundDatabaseHelper(requireContext());


        List<LostFoundItem> items = dbHelper.getAllItems();

        adapter = new ItemsAdapter(items, dbHelper);
        recyclerView.setAdapter(adapter);

        Button btnGoBack = view.findViewById(R.id.goBackBtn);
        btnGoBack.setOnClickListener(v->{
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        return view;
    }
}