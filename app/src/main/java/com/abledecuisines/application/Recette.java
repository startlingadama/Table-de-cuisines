package com.abledecuisines.application;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import com.abledecuisines.application.DetaiilsActivity;

class Recette{
    public String nom;
    public List<String> ingredients;
    public List<String> etapes;

    public void Recete(String nom, List<String> ingredients, List<String> etapes) {
        this.nom = nom;
        this.ingredients = ingredients;
        this.etapes = etapes;
    }

    public View getCarte(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.carte, null);
        TextView titre = view.findViewById(R.id.textView3);
        titre.setText(nom);
        Button details = view.findViewById(R.id.button4);
        details.setOnClickListener(v -> {
            Intent intent = new Intent(view.getContext(), DetaiilsActivity.class);
            intent.putExtra("nom", nom);
            view.getContext().startActivity(intent);});

        return view;
    };
}
