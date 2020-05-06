package com.example.covid19news.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.example.covid19news.Model.GlobalModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GlobalRecyclerViewAdapter extends RecyclerView.Adapter<GlobalRecyclerViewAdapter.ViewHolder> {

    private Context mContext;
    private List<GlobalModel> list = new ArrayList<>();

    public GlobalRecyclerViewAdapter(Context mContext, List<GlobalModel> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public GlobalRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull GlobalRecyclerViewAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
