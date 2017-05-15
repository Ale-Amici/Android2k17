package it.unitn.disi.lpsmt.idabere.activities;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import it.unitn.disi.lpsmt.idabere.R;

public class GpsLoadingActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private ProgressBar mLoadingIndicator;
    private Context mContext;

    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;

    private double mLatitude;
    private double mLongitude;

    String [] fakeData = {"A"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps_loading);

        initViewComps();
        mContext = this;

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        new LocationsLoader().execute(fakeData);

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        try {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
            if (mLastLocation != null) {
                mLatitude = mLastLocation.getLatitude();
                mLongitude = mLastLocation.getLongitude();
            }
        } catch (SecurityException ex) {
            Toast.makeText(this,"Permission Denied", Toast.LENGTH_SHORT);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    private void initViewComps () {

        mLoadingIndicator = (ProgressBar) findViewById(R.id.progressBar);

    }

    public class LocationsLoader extends AsyncTask<String[],String[],String[]>  {

        @Override
        protected String[] doInBackground(String[]... params) {



            SystemClock.sleep(3000);
            return new String[0];
        }

        @Override
        protected void onPostExecute(String[] strings) {
            super.onPostExecute(strings);
            Intent intent = new Intent();
            intent.setClass(mContext,SearchBarActivity.class);
            startActivity(intent);
        }

    }

}
