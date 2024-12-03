package com.abledecuisines.application;
import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class GestionnaireData {
    private static final String CLE_NOM = "app_prefs";
    private static final String CLE_LIST  = "list_dynamique";

    private SharedPreferences prefs;
    private List<String> liste;

    public GestionnaireData(Context context) {
        prefs = context.getSharedPreferences(CLE_NOM, Context.MODE_PRIVATE);
    }

    public void setListe(List<String> liste) {
        this.liste = liste;
        SharedPreferences.Editor editor = prefs.edit();
        JSONArray jliste = new JSONArray(liste);
        editor.putString(CLE_LIST, jliste.toString());
        editor.apply();

    }

    public List<String> getListe() {
        String jlisteString = prefs.getString(CLE_LIST, "[]");
        List<String> liste = new ArrayList<>();
        try{
            for(int i = 0; i < jliste.length(); i++){
                liste.add(jliste.getString(i));
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return liste;
    }

    public void charge_json_data_Prefs(Context contexte){
        Sting json = null;
        try{
            InputStream is = contexte.getAssets().open("sampledata/data.json");
            int taille = is.available();
            byte[] buffer = new byte[taille];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        }catch (IOException e){
            e.printStackTrace();
        }

        if(json != null){
            setListe(json);
        }
    }


}
