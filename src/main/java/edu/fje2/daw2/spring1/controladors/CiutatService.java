package edu.fje2.daw2.spring1.controladors;

import edu.fje2.daw2.spring1.model.Ciutat;
import edu.fje2.daw2.spring1.repositoris.CiutatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Servei per obtenir la previsió del temps de diverses ciutats utilitzant la API d'Open Meteo.
 */
@Service
public class CiutatService {
    /**
     * Llista de ciutats de les quals es vol obtenir la previsió.
     */
    List<Ciutat> ciutats = new ArrayList<>();
    @Autowired
    private CiutatRepository ciutatRepository;

    /**
     * Obté la previsió del temps de cada ciutat especificada i l'emmagatzema a la base de dades.
     * @param ciutats Llista de les ciutats de les quals es vol obtenir la previsió.
     */
    public void fetchPrevisioCiutats(List<Ciutat> ciutats) {
        for (Ciutat ciutat : ciutats) {
            try {
                URL url = new URL(ciutat.getConsultaFetch());
                //System.out.println("URL: " + ciutat.getConsultaFetch());
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String inputLine;
                    StringBuffer response = new StringBuffer();
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();
                    String previsioJSON = response.toString();
                    ciutat.setPrevisioJSON(previsioJSON);
                    ciutatRepository.save(ciutat);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Actualitza la previsió del temps de les ciutats especificades i l'emmagatzema a la base de dades.
     * En aquest mètode s'especifica la llista de ciutats que es volen actualitzar.
     */
    public void actualitzarPrevisioCiutats(){
        List<Ciutat> ciutats = ciutatRepository.findAll();
        fetchPrevisioCiutats(ciutats);
    }
}
