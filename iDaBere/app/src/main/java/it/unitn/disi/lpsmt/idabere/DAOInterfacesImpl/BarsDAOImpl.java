package it.unitn.disi.lpsmt.idabere.DAOInterfacesImpl;

import android.location.Address;
import android.net.Uri;
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

import it.unitn.disi.lpsmt.idabere.DAOIntefaces.BarsDAO;
import it.unitn.disi.lpsmt.idabere.models.Bar;
import it.unitn.disi.lpsmt.idabere.models.BarCounter;
import it.unitn.disi.lpsmt.idabere.models.DeliveryPlace;
import it.unitn.disi.lpsmt.idabere.models.BarTable;
import it.unitn.disi.lpsmt.idabere.models.TimeOpen;
import it.unitn.disi.lpsmt.idabere.deserializer.DeliveryPlaceDeserializer;
import it.unitn.disi.lpsmt.idabere.utils.BackendConnection;
import it.unitn.disi.lpsmt.idabere.utils.TimeOpenDeserializer;

/**
 * Created by giovanni on 15/05/2017.
 */

public class BarsDAOImpl implements BarsDAO {

    private final String API_BASE_URL = "http://151.80.152.226/";
    final String BARS_ROUTE = "bars";

    private GsonBuilder gsonBuilder = new GsonBuilder();

    private BackendConnection backendConnection = new BackendConnection();


    @Override
    public ArrayList<Bar> getAllBars() {
        ArrayList<Bar> results;
        String data;

        backendConnection.setBASE_URL(API_BASE_URL);
        backendConnection.buildUri();

        ArrayList<String> routes = new ArrayList<>();
        routes.add(BARS_ROUTE);
        backendConnection.setROUTES(routes);
        backendConnection.appendRoutes();

        backendConnection.buildURL();

        data = backendConnection.connectUrlGET();

        Type collectionType = new TypeToken<ArrayList<Bar>>(){}.getType();
        gsonBuilder.registerTypeAdapter(TimeOpen.class, new TimeOpenDeserializer());

        Gson gson = gsonBuilder.create();

        results = gson.fromJson(data, collectionType);

        return results;
    }

    @Override
    public ArrayList<Bar> getBarsByCoordinates (Address address) {
        ArrayList<Bar> results;
        String data;

        //Expected results
        double barLatitude = address.getLatitude();
        double barLongitude = address.getLongitude();


//        final String LATITUDE_PARAMETER = "latitude";
//        final String LONGITUDE_PARAMETER = "longitude";
//
//
//
//        Uri builtUri = Uri.parse(API_BASE_URL).buildUpon()
//                .appendPath(BARS_ROUTE)
//                .appendQueryParameter(LATITUDE_PARAMETER, String.valueOf(barLatitude))
//                .appendQueryParameter(LONGITUDE_PARAMETER, String.valueOf(barLongitude))
//                .build();
//
//        URL url = null;
//        try {
//            url = new URL(builtUri.toString());
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//
//        HttpURLConnection urlConnection = null;
//        try {
//            urlConnection = (HttpURLConnection) url.openConnection();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            InputStream inputStream = urlConnection.getInputStream();
//
//            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
//            StringBuilder sb = new StringBuilder();
//
//            String line = null;
//            while ((line = reader.readLine()) != null) {
//                sb.append(line + "\n");
//            }
//
//
//            data = sb.toString();
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            urlConnection.disconnect();
//        }
//
//        try {
//            Log.d("DATA", data.toString());
//        } catch (NullPointerException ex){
//
//        }

        backendConnection.setBASE_URL(API_BASE_URL);
        backendConnection.buildUri();

        ArrayList<String> routes = new ArrayList<>();
        routes.add(BARS_ROUTE);
        backendConnection.setROUTES(routes);
        backendConnection.appendRoutes();

        ArrayList<String> queryParameters = new ArrayList<>();
        queryParameters.add("latitude");
        queryParameters.add("longitude");
        backendConnection.setPARAMETERS(queryParameters);

        ArrayList<String> queryParametersValues = new ArrayList<>();
        queryParametersValues.add(Double.toString(barLatitude));
        queryParametersValues.add(Double.toString(barLongitude));
        backendConnection.setPARAMETERS_VALUES(queryParametersValues);

        backendConnection.appendQueryParametersGET();
        backendConnection.buildURL();

        data = backendConnection.connectUrlGET();

        Type collectionType = new TypeToken<ArrayList<Bar>>(){}.getType();
        gsonBuilder.registerTypeAdapter(TimeOpen.class, new TimeOpenDeserializer());

        Gson gson = gsonBuilder.create();

        results = gson.fromJson(data, collectionType);


        return results;
    }

    @Override
    public Bar getBarById(Bar bar) {
        Bar result;
        String data = null;

        int barId = bar.getId();

//        Uri builtUri = Uri.parse(API_BASE_URL).buildUpon()
//                .appendPath(BARS_ROUTE)
//                .appendPath(String.valueOf(barId))
//                .build();
//
//        Log.d("URL", builtUri.toString());
//
//        URL url = null;
//        try {
//            url = new URL(builtUri.toString());
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//
//
//        HttpURLConnection urlConnection = null;
//        try {
//            urlConnection = (HttpURLConnection) url.openConnection();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            InputStream inputStream = urlConnection.getInputStream();
//
//            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
//            StringBuilder sb = new StringBuilder();
//
//            String line = null;
//            while ((line = reader.readLine()) != null) {
//                sb.append(line + "\n");
//            }
//
//            data = sb.toString();
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            urlConnection.disconnect();
//        }
//
//        try {
//            Log.d("DATA", data.toString());
//        } catch (NullPointerException ex){
//
//        }
//
//
//        // TODO: Integrazione della libreria GSON per la costruzione del Bean ricevuto dal backend
//
//        GsonBuilder gsonBuilder = new GsonBuilder();

        backendConnection.setBASE_URL(API_BASE_URL);
        backendConnection.buildUri();

        ArrayList<String> routes = new ArrayList<>();
        routes.add(BARS_ROUTE);
        routes.add(Integer.toString(barId));
        backendConnection.setROUTES(routes);
        backendConnection.appendRoutes();

        backendConnection.buildURL();

        data = backendConnection.connectUrlGET();

        gsonBuilder.registerTypeAdapter(TimeOpen.class, new TimeOpenDeserializer());
        DeliveryPlaceDeserializer deserializer = new DeliveryPlaceDeserializer();
        deserializer.registerDeliveryPlace("tableNumber", BarTable.class);
        deserializer.registerDeliveryPlace("counterName", BarCounter.class);
        gsonBuilder.registerTypeAdapter(DeliveryPlace.class, deserializer);

        Gson gson = gsonBuilder.create();

        result = gson.fromJson(data, Bar.class);


        return result;
    }

    @Override
    public Bar getBarByName(String name) {
        return null;
    }

}
