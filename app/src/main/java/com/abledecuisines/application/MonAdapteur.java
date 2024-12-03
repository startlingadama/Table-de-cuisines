package com.abledecuisines.application;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Group;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONObject;

import java.util.List;

public class MonAdapteur extends RecyclerView.Adapter<MonAdapteur.MonViewHolder>{
    private List<JSONObject> itemLists;

    public MonAdapteur(List<JSONObject> itemLists) {
        this.itemLists = itemLists;
    }

    @NonNull
    @Override
    public MonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.carte, parent, false);

        return new MonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MonViewHolder holder, int position) {
        holder.textView.setText(itemLists.get(position).optString("nom"));
    }

    @Override
    public int getItemCount() {
        return itemLists.size();
    }

    static class MonViewHolder extends RecyclerView.ViewHolder {
        TextView textView;


        public MonViewHolder(@NonNull View carte) {
            super(carte);
            textView = carte.findViewById(R.id.textView3);

            carte.findViewById(R.id.button4).setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), DetaiilsActivity.class);
                intent.putExtra("nom", textView.getText());
                v.getContext().startActivity(intent);
            });

        }

    }

    public void update(List<JSONObject> recettes){
        this.itemLists = recettes;
        notifyDataSetChanged();
    }



}
