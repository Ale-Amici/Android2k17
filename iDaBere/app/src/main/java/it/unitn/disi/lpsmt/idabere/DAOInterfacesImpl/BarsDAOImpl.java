package it.unitn.disi.lpsmt.idabere.DAOInterfacesImpl;

import android.location.Address;
import android.net.Uri;
import android.util.JsonReader;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import it.unitn.disi.lpsmt.idabere.DAOIntefaces.BarsDAO;
import it.unitn.disi.lpsmt.idabere.Models.Bar;

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
        JSONArray data = null;

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

            try {
                data = new JSONArray(sb.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }


        try {

            results = new ArrayList<>();
            for (int i = 0; i <data.length(); i++) {
                JSONObject item = data.getJSONObject(i);
                Bar newBar = new Bar(item.getInt(BAR_ID),item.getString(BAR_NAME),null,null, null, null,null);
                results.add(newBar);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("BARS", results.toString());

        return results;
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
