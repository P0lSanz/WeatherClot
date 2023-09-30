package edu.fje2.daw2.spring1.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

public class Ciutat {
    @Id
    public String nom;
    public String consultaFetch;
    public String previsioJSON;

    public Ciutat() {}

    public Ciutat(String nom, String consultaFetch, String previsioJSON) {
        this.nom = nom;
        this.consultaFetch = consultaFetch;
        this.previsioJSON = previsioJSON;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom=nom;
    }

    public String getConsultaFetch() {
        return consultaFetch;
    }

    public void setConsultaFetch(String consultaFetch) {
        this.consultaFetch=consultaFetch;
    }

    public String getPrevisioJSON() {
        return previsioJSON;
    }

    public void setPrevisioJSON(String previsioJSON) {
        this.previsioJSON=previsioJSON;
    }

    public List<String> getPrevisionsFromJSON() throws JSONException {
        List<String> previsions = new ArrayList<>();

        JSONObject previsio = new JSONObject(previsioJSON);
        JSONArray weathercode = previsio.getJSONObject("daily").getJSONArray("weathercode");
        for (int i = 0; i < weathercode.length(); i++) {
            previsions.add(String.valueOf(weathercode.getInt(i)));
        }

        return previsions;
    }
}
