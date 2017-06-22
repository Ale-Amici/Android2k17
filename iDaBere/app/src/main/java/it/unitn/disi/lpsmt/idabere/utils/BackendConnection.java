package it.unitn.disi.lpsmt.idabere.utils;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import it.unitn.disi.lpsmt.idabere.models.Bar;
import it.unitn.disi.lpsmt.idabere.models.TimeOpen;

/**
 * Created by giovanni on 22/06/2017.
 */

public class BackendConnection {

    public ArrayList<String> errors;

    private String BASE_URL;
    private String [] ROUTES;
    private String [] PARAMETERS;
    private String [] PARAMETERS_VALUES;

    private Uri builtUri ;
    private URL builtURL;

    public BackendConnection () {}

    public void buildUri () {
        builtUri = Uri.parse(BASE_URL);
        Uri.Builder uriBuilder = builtUri.buildUpon();
        appendRoutes(uriBuilder);
        appendQueryParameters(uriBuilder);
        uriBuilder.build();

    }

    public void buildURL () {
        builtURL = null;
        try {
            builtURL = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            addError("URL Malformato");
            e.printStackTrace();
        }
    }

    private void appendRoutes(Uri.Builder uriBuilder){
        for (int i = 0; i < ROUTES.length; i++) {
            uriBuilder.appendPath(ROUTES[i]);
        }
    }

    private void appendQueryParameters(Uri.Builder uriBuilder) {
        for (int i = 0; i < PARAMETERS.length; i++) {
            uriBuilder.appendQueryParameter(PARAMETERS[i], PARAMETERS_VALUES[i]);
        }
    }

    private String connectUrl (URL url) {
        String data = "";
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            addError("Errore di IO");
            e.printStackTrace();
        }
        try {
            InputStream inputStream = urlConnection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }

            data = sb.toString();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }

        try {
            Log.d("DATA", data.toString());
        } catch (NullPointerException ex){
            addError("Nessun dato ricevuto");
            ex.printStackTrace();
        }

        return data;
    }

    public ArrayList<String> getErrors() {
        return errors;
    }

    public void addError (String error){
        if (getErrors() == null){
            errors = new ArrayList<>();
        }
        errors.add(error);
    }

    public String getBASE_URL() {
        return BASE_URL;
    }

    public void setBASE_URL(@NonNull String BASE_URL) {
        if (BASE_URL != null || BASE_URL.isEmpty()){
            this.BASE_URL = BASE_URL;
        } else {
            addError("Base URL Vuoto");
        }

    }

    public String[] getROUTES() {
        return ROUTES;
    }

    public void setROUTES(String[] ROUTES) {
        this.ROUTES = ROUTES;
    }

    public String[] getPARAMETERS() {
        return PARAMETERS;
    }

    public void setPARAMETERS(String[] PARAMETERS) {
        this.PARAMETERS = PARAMETERS;
    }

    public String[] getPARAMETERS_VALUES() {
        return PARAMETERS_VALUES;
    }

    public void setPARAMETERS_VALUES(String[] PARAMETERS_VALUES) {
        this.PARAMETERS_VALUES = PARAMETERS_VALUES;
    }
}
