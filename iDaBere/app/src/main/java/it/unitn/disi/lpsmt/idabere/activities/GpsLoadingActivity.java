package it.unitn.disi.lpsmt.idabere.activities;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import java.io.IOException;
import java.security.Permission;
import java.security.Permissions;
import java.util.List;
import java.util.Locale;

import it.unitn.disi.lpsmt.idabere.DAOIntefaces.FactoryDAO;
import it.unitn.disi.lpsmt.idabere.DAOInterfacesImpl.FactoryDAOImpl;
import it.unitn.disi.lpsmt.idabere.Manifest;
import it.unitn.disi.lpsmt.idabere.R;
import it.unitn.disi.lpsmt.idabere.utils.GpsLocationRetriever;

public class GpsLoadingActivity extends AppCompatActivity {

    //DAOFactory
    public final static FactoryDAOImpl factoryDAOImpl = new FactoryDAOImpl();

    private GpsLocationRetriever gpsLocationRetriever;

    String [] fakeData = {"A"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps_loading);

        initViewComps();

        gpsLocationRetriever = new GpsLocationRetriever(this,this);

        new LocationsLoader().execute(fakeData);

    }



    @Override
    protected void onStart() {
        gpsLocationRetriever.startConnection();
        super.onStart();
    }

    @Override
    protected void onStop() {
        gpsLocationRetriever.stopConnection();
        super.onStop();
    }

    private void initViewComps () {}


    public class LocationsLoader extends AsyncTask<String[],String[],String[]>  {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String[] doInBackground(String[]... params) {
            Address address = new Address(null);
            SystemClock.sleep(10000);


            address = gpsLocationRetriever.getmAddress();



            Log.d("ADDRESS", address.toString());

            GpsLoadingActivity.factoryDAOImpl.newBarsDAO().getBarByAddress(address);

            return new String[0];
        }

        @Override
        protected void onPostExecute(String[] strings) {
            super.onPostExecute(strings);
            Intent intent = new Intent();
            intent.setClass(GpsLoadingActivity.this, SearchBarActivity.class);
            startActivity(intent);
        }

    }



}
