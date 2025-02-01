package com.example.savorysync.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.savorysync.R;
import com.example.savorysync.model.IngredientDetails;

import java.util.List;

public class ListeDeCoursesAdapter extends RecyclerView.Adapter<ListeDeCoursesAdapter.ListeDeCoursesViewHolder> {

    private List<IngredientDetails> liste;
    private Context contexte;
    private int layoutResourceId;

    public ListeDeCoursesAdapter(Context context, int layoutResourceId, List<IngredientDetails> liste) {
        this.liste = liste;
        this.contexte = context;
        this.layoutResourceId = layoutResourceId;
    }

    @NonNull
    @Override
    public ListeDeCoursesAdapter.ListeDeCoursesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(layoutResourceId, parent, false);
        return new ListeDeCoursesAdapter.ListeDeCoursesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListeDeCoursesAdapter.ListeDeCoursesViewHolder holder, int position) {
        holder.display(liste.get(position));
    }

    @Override
    public int getItemCount() {
        return liste.size();
    }

    public void submitList(List<IngredientDetails> listeDeCoursesEntities) {
        this.liste = listeDeCoursesEntities;
        notifyDataSetChanged();
    }

    public static class ListeDeCoursesViewHolder extends RecyclerView.ViewHolder {
        TextView ingredientName;
        TextView ingredientQuantity;
        TextView ingredientUnite;

        public ListeDeCoursesViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredientName = itemView.findViewById(R.id.tvIngredientName);
            ingredientQuantity = itemView.findViewById(R.id.tvIngredientQuantity);
            ingredientUnite = itemView.findViewById(R.id.tvIngredientUnite);
        }

        void display(IngredientDetails liste){
            ingredientName.setText(String.valueOf(liste.getNom()));
            ingredientQuantity.setText(String.valueOf(liste.getTotalQuantite()));
            ingredientUnite.setText(String.valueOf(liste.getUnite()));
        }
    }
}
