package com.abledecuisines.application;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.Arrays;

public class AjoutActivity extends AppCompatActivity {
    private GestionnaireData gestionnaireData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ajout);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        gestionnaireData = new GestionnaireData(this);

        findViewById(R.id.button3).setOnClickListener(v -> {
            // Get references to the views
            EditText editText = findViewById(R.id.editTextText);
            TextInputEditText textInputLayout = findViewById(R.id.multInput);
            AutoCompleteTextView autoCompleteTextView = findViewById(R.id.multiAutoCompleteTextView);

            if (editText != null && textInputLayout != null && autoCompleteTextView != null) {
                // Get the text from the views
                String nom = editText.getText().toString();
                String etapes = textInputLayout.getText().toString();
                String ingredients = autoCompleteTextView.getText().toString();

                // Create a new JSONObject
                JSONObject recette = new JSONObject();

                try {
                    // Add data to the JSON object
                    recette.put("nom", nom);
                    Log.d("etapes", etapes);

                    // Split strings by newline and put in JSONArray
                    String[] etapesArray = etapes.split("\n");
                    recette.put("etapes", Arrays.asList(etapesArray));
                    String[] ingredientsArray = ingredients.split("\n");
                    recette.put("ingredients", Arrays.asList(ingredientsArray));

                    // Add the data to the gestionnaire
                    gestionnaireData.ajouter(recette);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // Show a toast message
                Toast.makeText(this, "Ajout effectuée", Toast.LENGTH_SHORT).show();

                // Start the next activity
                Intent intent1 = new Intent(this, ConsultActivity.class);
                startActivity(intent1);
                finish();
            } else {
                Log.e("Error", "One or more views are null");
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        getWindow().setStatusBarColor(getResources().getColor(R.color.couleur_primaire));
    }

}