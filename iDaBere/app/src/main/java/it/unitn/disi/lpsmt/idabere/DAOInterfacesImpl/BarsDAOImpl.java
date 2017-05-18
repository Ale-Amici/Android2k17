package it.unitn.disi.lpsmt.idabere.DAOInterfacesImpl;

import android.location.Address;
import android.net.Uri;
import android.util.JsonReader;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
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

    @Override
    public Bar getBarByAddress(Address address) {
        Bar result = null;

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
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                Log.d("RESULT",scanner.next());
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }


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
