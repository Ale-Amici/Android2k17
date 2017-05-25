package it.unitn.disi.lpsmt.idabere.DAOInterfacesImpl;

import android.location.Address;
import android.net.Uri;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import it.unitn.disi.lpsmt.idabere.DAOIntefaces.BarsDAO;
import it.unitn.disi.lpsmt.idabere.models.Bar;
import it.unitn.disi.lpsmt.idabere.models.TimeOpen;
import it.unitn.disi.lpsmt.idabere.utils.TimeOpenDeserializer;

/**
 * Created by giovanni on 15/05/2017.
 */

public class BarsDAOImpl implements BarsDAO {

    private final String BARS_API_BASE_URL = "http://151.80.152.226/";
    final String BARS_ROUTE = "bars";

    private final String BAR_ID = "id";
    private final String BAR_NAME = "name";
    private final String BAR_ADDRESS = "address";
    private final String BAR_OPENING_HOURS_LIST = "openingHours";
    private final String BAR_LATITUDE = "latitude";
    private final String BAR_LONGITUDE = "longitude";
    private final String BAR_DAY_OF_WEEK = "dayOfWeek";
    private final String BAR_TIME_OPEN = "timeOpen";
    private final String BAR_WORKING_TIME = "workingTime";
    private final String BAR_DISTANCE = "distance";


    @Override
    public ArrayList<Bar> getBarsByCoordinates (Address address) {
        ArrayList<Bar> results = null;
        String data = null;

        //Expected results
        double barLatitude = address.getLatitude();
        double barLongitude = address.getLongitude();


        final String LATITUDE_PARAMETER = "latitude";
        final String LONGITUDE_PARAMETER = "longitude";


        Uri builtUri = Uri.parse(BARS_API_BASE_URL).buildUpon()
                .appendPath(BARS_ROUTE)
                .appendQueryParameter(LATITUDE_PARAMETER, String.valueOf(barLatitude))
                .appendQueryParameter(LONGITUDE_PARAMETER, String.valueOf(barLongitude))
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
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

        Log.d("DATA", data.toString());

        GsonBuilder gsonBuilder = new GsonBuilder();

        Type collectionType = new TypeToken<ArrayList<Bar>>(){}.getType();

        gsonBuilder.registerTypeAdapter(TimeOpen.class, new TimeOpenDeserializer());

        Gson gson = gsonBuilder.create();

        ArrayList<Bar> imageResults = gson.fromJson(data, collectionType);

        Log.d("RESULT",imageResults.get(0).getOpeningHours().toString());


       /* try {

            results = new ArrayList<>();
            for (int i = 0; i <data.length(); i++) {
                JSONObject item = data.getJSONObject(i);
                Bar newBar = new Bar();
                newBar.setId(item.getInt(BAR_ID));
                results.add(newBar);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }*/

        return results;
    }

    @Override
    public Bar getBarById(Bar bar) {
        Bar result = null;
        JSONObject data = null;

        int barId = bar.getId();

        Uri builtUri = Uri.parse(BARS_API_BASE_URL).buildUpon()
                .appendPath(BARS_ROUTE)
                .appendPath(String.valueOf(barId))
                .build();

        Log.d("URL", builtUri.toString());

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
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

            try {
                data = new JSONObject(sb.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }

        Log.d("DATA", data.toString());

        // TODO: Integrazione della libreria GSON per la costruzione del Bean ricevuto dal backend

        return result;
    }

    @Override
    public ArrayList<Bar> getAllBars() {
        return null;
    }

    @Override
    public Bar getBarByName(String name) {
        return null;
    }

}
