package com.example.savorysync.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.savorysync.R;
import com.example.savorysync.model.entity.ListeDeCoursesEntity;
import com.example.savorysync.model.entity.RecetteEntity;

import java.time.LocalDate;
import java.util.List;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.DateViewHolder> {

    private List<LocalDate> dates;
    private final OnListeClickListener listener;
    private final int layoutResourceId;

    public ShoppingListAdapter(Context context, int layoutResourceId, List<LocalDate> dates, OnListeClickListener listener) {
        this.dates = dates;
        this.listener = listener;
        this.layoutResourceId = layoutResourceId;
    }

    /*public interface OnListeClickListener {
        void onListeClick(ListeDeCoursesEntity liste);
    }*/

    public interface OnListeClickListener {
        void onListeClick(LocalDate date);
    }


    @NonNull
    @Override
    public DateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(layoutResourceId, parent, false);
        return new DateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DateViewHolder holder, int position) {
        LocalDate date = dates.get(position);
        holder.textView.setText("Liste de courses du " + date.toString());
        holder.itemView.setOnClickListener(v -> listener.onListeClick(date));

    }

    @Override
    public int getItemCount() {
        return dates.size();
    }

    public void updateData(List<LocalDate> newDates) {
        dates = newDates;
        notifyDataSetChanged();
    }

    static class DateViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public DateViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tvOptionName);
        }
    }
}
