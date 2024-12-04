package com.abledecuisines.application;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AccueilActivity extends AppCompatActivity {
    GestionnaireData gestionnaireData;
    private  Toast toast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_accueil);

        gestionnaireData = new GestionnaireData(this);
        gestionnaireData.charge_json_data_Prefs(this);

        findViewById(R.id.button).setOnClickListener(v -> {
            Intent intent = new Intent(this, AjoutActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.button2).setOnClickListener(v -> {
            Intent intent = new Intent(this, ConsultActivity.class);
            startActivity(intent);
        });

        toast = Toast.makeText(this, "Vous devez avoir l'internet pour voir la photo de chaque recette.", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 100);

    }
    @Override
    protected void onResume() {
        super.onResume();
        getWindow().setStatusBarColor(getResources().getColor(R.color.couleur_primaire));

        toast.show();
    }

}