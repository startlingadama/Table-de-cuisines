package com.abledecuisines.application;
import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class GestionnaireData {
    private static final String CLE_NOM = "app_prefs";
    private static final String CLE_LIST  = "list_dynamique";

    private SharedPreferences prefs;
    private List<JSONObject> liste;

    public GestionnaireData(Context context) {
        prefs = context.getSharedPreferences(CLE_NOM, Context.MODE_PRIVATE);
    }

    public void setListe(List<JSONObject> liste) {
        this.liste = liste;
        SharedPreferences.Editor editor = prefs.edit();
        JSONArray jliste = new JSONArray(liste);
        editor.putString(CLE_LIST, jliste.toString());
        editor.apply();

    }

    public List<JSONObject> getListe() {
        String jlisteString = prefs.getString(CLE_LIST, "[]");
        List<JSONObject> liste = new ArrayList<>();
        try{
            JSONArray jliste = new JSONArray(jlisteString);
            for(int i = 0; i < jliste.length(); i++){
                liste.add(jliste.getJSONObject(i));
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return liste;
    }

    public void delete(String nom){
        List<JSONObject> liste = getListe();
        for(int i = 0; i < liste.size(); i++){
            try{
                if(liste.get(i).getString("nom").equals(nom)){
                    liste.remove(i);
                    prefs.edit().putString(CLE_LIST, new JSONArray(liste).toString()).apply();
                    prefs.edit().commit();
                    break;
                }
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
    }

    public void ajouter(JSONObject recette){
        List<JSONObject> liste = getListe();
        liste.add(recette);
        setListe(liste);
    }

    public void modifier(JSONObject recette, String nom){
        List<JSONObject> liste = getListe();
        for (int i = 0; i < liste.size(); i++) {
            try {
                if (liste.get(i).getString("nom").equals(nom)) {
                    liste.set(i, recette);
                    setListe(liste);
                    break;
                }
            }catch(JSONException e){
                e.printStackTrace();
            }
        }
    }
    public void charge_json_data_Prefs(Context contexte){
        JSONArray jsonArray = null;
        try{
            InputStream is = contexte.getResources().openRawResource(R.raw.data);
            if(is == null){
                throw new IOException("Fichier introuvable");
            }
            int taille = is.available();
            byte[] buffer = new byte[taille];
            is.read(buffer);
            is.close();
            String jsons = new String(buffer, "UTF-8");
            jsonArray = new JSONArray(jsons);
        }catch (IOException | JSONException e){
            e.printStackTrace();
        }

        if(jsonArray != null){
            List<JSONObject> liste = new ArrayList<>();
            try {
                for (int i = 0; i < jsonArray.length(); i++) {
                    liste.add(jsonArray.getJSONObject(i));
                }
                setListe(liste);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


}
