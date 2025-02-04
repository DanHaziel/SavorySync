package com.example.savorysync.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.savorysync.R;
import com.example.savorysync.model.AppDatabase;
import com.example.savorysync.model.dao.IngredientDao;
import com.example.savorysync.model.entity.RecetteEntity;

import java.util.ArrayList;
import java.util.List;

public class RecetteAdapter extends RecyclerView.Adapter<RecetteAdapter.RecetteHolder> {

    private List<RecetteEntity> recette;
    private Context contexte;
    private int layoutResourceId;
    private IngredientAdapter ingredientAdapter = new IngredientAdapter(new ArrayList<>());
    private IngredientDao ingredientDao;

    public RecetteAdapter(Context context, int layoutResourceId, List<RecetteEntity> recette) {
        this.recette = recette;
        this.contexte = context;
        this.layoutResourceId = layoutResourceId;
        AppDatabase db = AppDatabase.getDatabaseFromJava(contexte);
        ingredientDao = db.ingredientDao();
    }

    @NonNull
    @Override
    public RecetteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(layoutResourceId, parent, false);
        return new RecetteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecetteHolder holder, int position) {
        holder.display(recette.get(position));
    }

    @Override
    public int getItemCount() {
        return recette.size();
    }

    public void submitList(List<RecetteEntity> recetteEntities) {
        this.recette = recetteEntities; // Met à jour la liste interne
        notifyDataSetChanged();       // Notifie RecyclerView que les données ont changé
    }

    public void submitList(RecetteEntity recetteEntity) {
        //this.recette = recetteEntity; // Met à jour la liste interne
        notifyDataSetChanged();       // Notifie RecyclerView que les données ont changé
    }

    public class RecetteHolder extends RecyclerView.ViewHolder {
        TextView nomRecetteTextView, personnesTextView, instructionsTextView;
        RecyclerView ingredientsRecyclerview;

        public RecetteHolder(@NonNull View itemView) {
            super(itemView);
            nomRecetteTextView = itemView.findViewById(R.id.nom_recette);
            personnesTextView = itemView.findViewById(R.id.personnes);
            instructionsTextView = itemView.findViewById(R.id.instructions);
            ingredientsRecyclerview = itemView.findViewById(R.id.ingredientsRecyclerview);
        }

        void display(RecetteEntity recette) {
            nomRecetteTextView.setText(recette.getTitre());
            personnesTextView.setText(String.valueOf(recette.getPersonnes()));
            instructionsTextView.setText(recette.getInstructions());
            ingredientsRecyclerview.setLayoutManager(new LinearLayoutManager(contexte));
            ingredientsRecyclerview.setAdapter(ingredientAdapter);

            // Supposons que le contexte est une instance de LifecycleOwner
            if (contexte instanceof LifecycleOwner) {
                ingredientDao.getAllByRecipeId(recette.getId())
                        .observe((LifecycleOwner) contexte, ingredientAdapter::submitList);
            } else {
                Log.e("RecetteAdapter", "Contexte n'est pas une instance de LifecycleOwner. Impossible d'observer LiveData.");
            }
        }
    }
}
