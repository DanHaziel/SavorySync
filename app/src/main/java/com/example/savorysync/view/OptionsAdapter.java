package com.example.savorysync.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.savorysync.R;
import com.example.savorysync.model.entity.RecetteEntity;

import java.util.List;

public class OptionsAdapter extends RecyclerView.Adapter<OptionsAdapter.OptionHolder> {

    private final List<RecetteEntity> recettes;
    private final OnRecipeClickListener listener;
    private final int layoutResourceId;

    public OptionsAdapter(Context context, int layoutResourceId, List<RecetteEntity> recettes, OnRecipeClickListener listener) {
        this.recettes = recettes;
        this.listener = listener;
        this.layoutResourceId = layoutResourceId;
    }

    public interface OnRecipeClickListener {
        void onRecipeClick(RecetteEntity recipe);
    }

    @NonNull
    @Override
    public OptionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(layoutResourceId, parent, false);
        return new OptionHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OptionHolder holder, int position) {
        RecetteEntity recette = recettes.get(position);
        holder.optionName.setText(recette.getTitre()); // Afficher le nom de la recette

        // Gérer le clic sur l'élément
        holder.itemView.setOnClickListener(v -> listener.onRecipeClick(recette));
    }

    @Override
    public int getItemCount() {
        return recettes.size();
    }

    public static class OptionHolder extends RecyclerView.ViewHolder {
        TextView optionName;

        public OptionHolder(@NonNull View itemView) {
            super(itemView);
            optionName = itemView.findViewById(R.id.tvOptionName);
        }
    }
}
