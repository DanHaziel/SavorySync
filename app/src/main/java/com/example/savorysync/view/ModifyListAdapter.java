package com.example.savorysync.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.savorysync.R;
import com.example.savorysync.model.IngredientDetails;

import java.util.List;

public class ModifyListAdapter  extends RecyclerView.Adapter<ModifyListAdapter.ModifyListViewHolder> {

    private List<IngredientDetails> liste;
    private Context contexte;
    private int layoutResourceId;

    public ModifyListAdapter(Context context, int layoutResourceId, List<IngredientDetails> liste) {
        this.liste = liste;
        this.contexte = context;
        this.layoutResourceId = layoutResourceId;
    }

    @NonNull
    @Override
    public ModifyListAdapter.ModifyListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(layoutResourceId, parent, false);
        return new ModifyListAdapter.ModifyListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ModifyListAdapter.ModifyListViewHolder holder, int position) {
        holder.display(liste.get(position), this, position);
    }

    @Override
    public int getItemCount() {
        return liste.size();
    }

    public void submitList(List<IngredientDetails> listeDeCoursesEntities) {
        this.liste = listeDeCoursesEntities;
        notifyDataSetChanged();
    }

    public List<IngredientDetails> getUpdatedList() {
        return liste;
    }

    public void updateIngredientQuantity(int position, int newQuantity) {
        liste.get(position).setTotalQuantite(newQuantity);
    }

    public static class ModifyListViewHolder extends RecyclerView.ViewHolder {
        TextView ingredientName;
        EditText ingredientQuantity;
        TextView ingredientUnite;

        public ModifyListViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredientName = itemView.findViewById(R.id.tvIngredientName);
            ingredientQuantity = itemView.findViewById(R.id.tvIngredientQuantity);
            ingredientUnite = itemView.findViewById(R.id.tvIngredientUnite);
        }

        void display(IngredientDetails ingredient, ModifyListAdapter adapter, int position) {
            ingredientName.setText(ingredient.getNom());
            ingredientQuantity.setText(String.valueOf(ingredient.getTotalQuantite()));
            ingredientUnite.setText(ingredient.getUnite());

            // Écoute les modifications sur la quantité
            ingredientQuantity.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (!s.toString().isEmpty()) {
                        int newQuantity = Integer.parseInt(s.toString());
                        adapter.updateIngredientQuantity(position, newQuantity);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {}
            });
        }
    }

}

