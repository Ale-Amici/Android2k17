package it.unitn.disi.lpsmt.idabere.utils;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import it.unitn.disi.lpsmt.idabere.activities.LoginActivity;
import it.unitn.disi.lpsmt.idabere.models.Bar;
import it.unitn.disi.lpsmt.idabere.models.TimeOpen;

/**
 * Created by giovanni on 22/06/2017.
 */

public class BackendConnection {

    private final String USER_AGENT = "Mozilla/5.0";

    public ArrayList<String> errors;

    private String BASE_URL;
    private ArrayList<String> ROUTES;
    private ArrayList<String> PARAMETERS;
    private ArrayList<String> PARAMETERS_VALUES;

    private Uri.Builder uriBuilder;

    private Uri builtUri;
    private URL builtURL;

    private RequestQueue queue;

    public BackendConnection(RequestQueue requestQueue) {
        queue = requestQueue;
    }

    private void addError(String error) {
        if (getErrors() == null) {
            errors = new ArrayList<>();
        }
        errors.add(error);
    }

//    private String getPostParametersAsString () {
//        String result = "";
//        int i;
//        for (i = 0; i < PARAMETERS.size() -1; i++) {
//            result += PARAMETERS.get(i) + "=" + PARAMETERS_VALUES.get(i) + "&";
//        }
//        result += PARAMETERS.get(i) + "=" + PARAMETERS_VALUES.get(i);
//        return result;
//    }

    private String readBuffer(HttpURLConnection urlConnection) {
        String result = "";
        BufferedReader in = null;
        try {
            in = new BufferedReader(
                    new InputStreamReader(urlConnection.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String inputLine;
        StringBuffer response = new StringBuffer();

        try {
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        result = response.toString();
        return result;
    }

    public void appendRoutes() {
        for (int i = 0; i < ROUTES.size(); i++) {
            uriBuilder.appendPath(ROUTES.get(i));
        }
    }

    public void appendQueryParametersGET() {
        for (int i = 0; i < PARAMETERS.size(); i++) {
            uriBuilder.appendQueryParameter(PARAMETERS.get(i), PARAMETERS_VALUES.get(i));
        }
    }

    public void buildUri() {
        builtUri = Uri.parse(BASE_URL);
        uriBuilder = builtUri.buildUpon();
    }

    public void buildURL() {
        builtUri = uriBuilder.build();
        builtURL = null;
        try {
            builtURL = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            addError("URL Malformato");
            e.printStackTrace();
        }
    }

    public String connectUrlPOST() {
        String data = "";

//        HttpURLConnection urlConnection = null;
//
//        try {
//            urlConnection = (HttpURLConnection) builtURL.openConnection();
//        } catch (IOException e) {
//            addError("Errore di IO");
//            e.printStackTrace();
//        }
//        //add reuqest header
//        try {
//            urlConnection.setRequestMethod("POST");
//        } catch (ProtocolException e) {
//            e.printStackTrace();
//        }
//        urlConnection.setRequestProperty("User-Agent", USER_AGENT);
//        urlConnection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
//
//        String urlParameters = getPostParametersAsString();
//
//        // Send post request
//        urlConnection.setDoOutput(true);
//
//        DataOutputStream wr = null;
//        try {
//            wr = new DataOutputStream(urlConnection.getOutputStream());
//            wr.writeBytes(urlParameters);
//            wr.flush();
//            wr.close();
//        } catch (IOException e) {
//            errors.add("Errore di IO in DataOutputStream");
//            e.printStackTrace();
//        }
//
//        int responseCode = 0;
//        try {
//            responseCode = urlConnection.getResponseCode();
//        } catch (IOException e) {
//            errors.add("Errore nel get response code");
//            e.printStackTrace();
//        }
//
//        System.out.println("\nSending 'POST' request to URL : " + builtURL.toString());
//        System.out.println("Post parameters : " + urlParameters);
//        System.out.println("Response Code : " + responseCode);
//
//        if (responseCode == 200){
//            //print result
//            System.out.println(readBuffer(urlConnection));
//        }

        // Instantiate the RequestQueue.
        String url = builtURL.toString();


        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("RESPONSE", "user not found");
                    }
                }
        ) {


            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(PARAMETERS.get(0), PARAMETERS_VALUES.get(0));
                params.put(PARAMETERS.get(1), PARAMETERS_VALUES.get(1));

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                return params;
            }
        };


        // Add the request to the RequestQueue.
        queue.add(postRequest);

        return data;
    }


    public String connectUrlGET() {

        String data = "";

        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) builtURL.openConnection();
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
        } catch (NullPointerException ex) {
            addError("Nessun dato ricevuto");
            ex.printStackTrace();
        }

        return data;
    }

    public ArrayList<String> getErrors() {
        return errors;
    }


    public String getBASE_URL() {
        return BASE_URL;
    }

    public void setBASE_URI(@NonNull String BASE_URL) {
        if (BASE_URL != null || BASE_URL.isEmpty()) {
            this.BASE_URL = BASE_URL;
        } else {
            addError("Base URL Vuoto");
        }

    }

    public ArrayList<String> getROUTES() {
        return ROUTES;
    }

    public void setROUTES(ArrayList<String> ROUTES) {
        this.ROUTES = ROUTES;
    }

    public ArrayList<String> getPARAMETERS() {
        return PARAMETERS;
    }

    public void setPARAMETERS(ArrayList<String> PARAMETERS) {
        this.PARAMETERS = PARAMETERS;
    }

    public ArrayList<String> getPARAMETERS_VALUES() {
        return PARAMETERS_VALUES;
    }

    public void setPARAMETERS_VALUES(ArrayList<String> PARAMETERS_VALUES) {
        this.PARAMETERS_VALUES = PARAMETERS_VALUES;
    }
}
