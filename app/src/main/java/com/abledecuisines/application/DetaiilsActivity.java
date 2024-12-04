package com.abledecuisines.application;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class DetaiilsActivity extends AppCompatActivity {
    private GestionnaireData gestionnaireData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detaiils);


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
        ImageView imageView = findViewById(R.id.imageView2);
        for (JSONObject recette : gestionnaireData.getListe()) {

            try {
                if (recette.getString("nom").equals(nom)) {
                    JSONArray ingredientsArray = recette.getJSONArray("ingredients");
                    StringBuilder result1 = new StringBuilder();

                    String imageUrl = recette.optString("img_url", "");
                    if (imageUrl == null || imageUrl.trim().isEmpty()) {


                        Picasso.get()
                                .load(R.mipmap.ic_launcher)
                                .transform(new CropCircleTransformation())
                                .into(imageView);
                    } else {

                        Picasso.get()
                                .load(imageUrl)
                                .placeholder(R.mipmap.ic_launcher)
                                .transform(new CropCircleTransformation())
                                .error(R.mipmap.ic_launcher_round)
                                .into(imageView);
                    }

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
            AlertDialog.Builder builder = new AlertDialog.Builder(this);


            builder.setTitle("Confirmation");
            builder.setMessage("Êtes-vous sûr de vouloir supprimer cette recette ?");

            builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    gestionnaireData.delete(nom);
                    confirm(true);
                    dialog.dismiss();
                    finish();
                }
            });


            builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    confirm(false);
                    dialog.dismiss();

                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        getWindow().setStatusBarColor(getResources().getColor(R.color.couleur_primaire));
    }

    public void confirm(boolean b){
        if(b){
            Toast.makeText(this, "Recette supprimée", Toast.LENGTH_SHORT).show();
        }


    }
}