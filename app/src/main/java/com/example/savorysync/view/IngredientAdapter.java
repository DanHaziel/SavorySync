package com.example.savorysync.view;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.savorysync.R;
import com.example.savorysync.model.entity.IngredientEntity;
import com.example.savorysync.model.entity.RecetteEntity;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {

    private List<IngredientEntity> ingredientList;

    public IngredientAdapter(List<IngredientEntity> ingredientList) {
        this.ingredientList = ingredientList;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_shopping_list, parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        IngredientEntity ingredient = ingredientList.get(position);
        holder.name.setText(ingredient.getNom());
        holder.quantity.setText(String.valueOf(ingredient.getQuantite()));
        holder.unit.setText(ingredient.getUnite());

        // Modifier dynamiquement les marges
        ViewGroup.MarginLayoutParams layoutParams =
                (ViewGroup.MarginLayoutParams) holder.itemView.getLayoutParams();

        if (layoutParams != null) {
            layoutParams.setMargins(4, 0, 4, 2); // Gauche, Haut, Droite, Bas en pixels
            holder.itemView.setLayoutParams(layoutParams);
        }
    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    public void submitList(List<IngredientEntity> recetteEntities) {
        Log.d("IngredientAdapter", "submitList: " + ingredientList);
        this.ingredientList = recetteEntities; // Met à jour la liste interne
        notifyDataSetChanged();       // Notifie RecyclerView que les données ont changé
        Log.d("Sortie", "Fin");
    }

    static class IngredientViewHolder extends RecyclerView.ViewHolder {
        TextView name, quantity, unit;

        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvIngredientName);
            quantity = itemView.findViewById(R.id.tvIngredientQuantity);
            unit = itemView.findViewById(R.id.tvIngredientUnite);
        }
    }
}

