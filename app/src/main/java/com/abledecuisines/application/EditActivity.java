package com.abledecuisines.application;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class EditActivity extends AppCompatActivity {

    private EditText editText;
    private TextInputEditText textInputLayout ;
    private AutoCompleteTextView autoCompleteTextView ;
    private GestionnaireData gestionnaireData;
    private String nom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit);


        Intent intent = getIntent();
        nom = intent.getStringExtra("nom");

        gestionnaireData = new GestionnaireData(this);

        editText = findViewById(R.id.editTextText2);
        textInputLayout = findViewById(R.id.multligne_mod);
        autoCompleteTextView = findViewById(R.id.multiAutoCompleteTextView2);

        editText.setText(nom);
        for (JSONObject recette : gestionnaireData.getListe()) {

            try {
                if (recette.getString("nom").equals(nom)) {
                    JSONArray ingredientsArray = recette.getJSONArray("ingredients");
                    JSONArray etapesArray = recette.getJSONArray("etapes");
                    StringBuilder result1 = new StringBuilder();

                    for (int i = 0; i < ingredientsArray.length(); i++) {
                        result1.append(ingredientsArray.getString(i));
                        if (i < ingredientsArray.length() - 1) {
                            result1.append(", ");
                        }

                    }
                    autoCompleteTextView.setText(result1.toString());

                    StringBuilder result2 = new StringBuilder();
                    for (int i = 0; i < etapesArray.length(); i++) {
                        result2.append(etapesArray.getString(i));

                        if (i < etapesArray.length() - 1) {
                            result2.append("\n");
                        }
                    }
                    textInputLayout.setText(result2.toString());

                    textInputLayout.setText(result2.toString());
                    break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        findViewById(R.id.button6).setOnClickListener(v -> {
            Intent intent1 = new Intent(v.getContext(), ConsultActivity.class);

            if (editText != null && textInputLayout != null && autoCompleteTextView != null) {

                nom = editText.getText().toString();
                String etapes = textInputLayout.getText().toString();
                String ingredients = autoCompleteTextView.getText().toString();


                JSONObject recette = new JSONObject();

                try {

                    recette.put("nom", nom);



                    String[] etapesArray = etapes.split("\n");
                    recette.put("etapes", Arrays.asList(etapesArray));
                    String[] ingredientsArray = ingredients.split("\n");
                    recette.put("ingredients", Arrays.asList(ingredientsArray));


                    gestionnaireData.modifier(recette, "nom");


                } catch (JSONException e) {
                    e.printStackTrace();
                }



            v.getContext().startActivity(intent1);
            Toast.makeText(this, "Modification effectuée", Toast.LENGTH_SHORT).show();
            finish();


    }});
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWindow().setStatusBarColor(getResources().getColor(R.color.couleur_primaire));
    }
}