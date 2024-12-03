package com.abledecuisines.application;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abledecuisines.application.GestionnaireData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ConsultActivity extends AppCompatActivity {
    private MonAdapteur adapteur;
    private RecyclerView recyclerView;
    private List<JSONObject> recettes;
    private GestionnaireData gestionnaireData;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_consult);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recycleView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        gestionnaireData = new GestionnaireData(this);

        radioGroup = findViewById(R.id.radioGroup);
        radioGroup.check(R.id.radioButton4);

        recettes = gestionnaireData.getListe();

        findViewById(R.id.radioButton4).setOnClickListener( v ->{
            if( v.getId() == R.id.radioButton4 && ((RadioButton)v).isChecked()){
                filtre(((RadioButton)v).getText().toString());
            }
        });

        findViewById(R.id.radioButton).setOnClickListener( v ->{
            if( v.getId() == R.id.radioButton && ((RadioButton)v).isChecked()){
                filtre(((RadioButton)v).getText().toString());
            }
        });

        findViewById(R.id.radioButton2).setOnClickListener( v ->{
            if( v.getId() == R.id.radioButton2 && ((RadioButton)v).isChecked()){
                filtre(((RadioButton)v).getText().toString());
            }
        });

        findViewById(R.id.radioButton3).setOnClickListener( v ->{
            if( v.getId() == R.id.radioButton3 && ((RadioButton)v).isChecked()){
                filtre(((RadioButton)v).getText().toString());
            }
        });


        adapteur = new MonAdapteur(recettes);
        recyclerView.setAdapter(adapteur);


    }

    @Override
    protected void onResume() {
        super.onResume();
        getWindow().setStatusBarColor(getResources().getColor(R.color.couleur_primaire));

        recettes = gestionnaireData.getListe();
        int selectedId = radioGroup.getCheckedRadioButtonId();

        if (selectedId != -1) {

            RadioButton selectedRadioButton = findViewById(selectedId);
            String selectedText = selectedRadioButton.getText().toString();
            Toast.makeText(this, "Sélectionné : " + selectedText, Toast.LENGTH_SHORT).show();

        }

        this.adapteur.update(recettes);
    }

    private void filtre(String categorie){
        recettes.clear();
        recettes = gestionnaireData.getListe();
        switch (categorie){
            case "entrée":
                for(int i = 0; i < recettes.size(); i++){
                    try {
                        if(!recettes.get(i).getString("categorie").contains("Entrée")){
                            recettes.remove(i);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                this.adapteur.update(recettes);
                break;

            case "plat principal":

                for(int i = 0; i < recettes.size(); i++){
                    try {
                        if(!recettes.get(i).getString("categorie").contains("Plat principal")){
                            recettes.remove(i);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }}

                this.adapteur.update(recettes);
                break;
            case "tout":

                this.adapteur.update(recettes);
                break;
            case "dessert":

                for(int i = 0; i < recettes.size(); i++){
                    try {
                        if(!recettes.get(i).getString("categorie").contains("Dessert")){
                            recettes.remove(i);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                this.adapteur.update(recettes);
                break;
        }
    }

}