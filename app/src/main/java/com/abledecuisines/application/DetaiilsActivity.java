package com.abledecuisines.application;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class DetaiilsActivity extends AppCompatActivity {
    private GestionnaireData gestionnaireData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detaiils);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        String nom = intent.getStringExtra("nom");

        TextView textView = findViewById(R.id.textView6);
        textView.setText(nom);

        findViewById(R.id.button5).setOnClickListener(v -> {
            Intent intent1 = new Intent(v.getContext(), EditActivity.class);
            intent1.putExtra("nom", nom);
            v.getContext().startActivity(intent1);
        });

        gestionnaireData = new GestionnaireData(this);

        TextView textView11 = findViewById(R.id.textView11);
        LinearLayout group = findViewById(R.id.layout1);
        for (JSONObject recette : gestionnaireData.getListe()) {

            try {
                if (recette.getString("nom").equals(nom)) {
                    JSONArray ingredientsArray = recette.getJSONArray("ingredients");
                    StringBuilder result1 = new StringBuilder();
                    for (int i = 0; i < ingredientsArray.length(); i++) {
                        result1.append(ingredientsArray.getString(i));
                        if (i < ingredientsArray.length() - 1) {
                            result1.append(", ");
                        }
                    }
                    textView11.setText(result1.toString());

                    JSONArray etapesArray = recette.getJSONArray("etapes");

                    for (int i = 0; i < etapesArray.length(); i++) {
                        TextView textView_ = new TextView(group.getContext());

                        textView_.setText(etapesArray.getString(i));
                        group.addView(textView_);
                    }
                    break;
                }
                } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        findViewById(R.id.button7).setOnClickListener(v -> {
            gestionnaireData.delete(nom);
            Toast.makeText(this, "Recette supprimée", Toast.LENGTH_SHORT).show();

            finish();
        });




    }

    @Override
    protected void onResume() {
        super.onResume();
        getWindow().setStatusBarColor(getResources().getColor(R.color.couleur_primaire));
    }
}