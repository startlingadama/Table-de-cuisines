package com.abledecuisines.application;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AccueilActivity extends AppCompatActivity {
    GestionnaireData gestionnaireData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_accueil);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
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

    }
    @Override
    protected void onResume() {
        super.onResume();
        getWindow().setStatusBarColor(getResources().getColor(R.color.couleur_primaire));
    }

}