package it.unitn.disi.lpsmt.idabere.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;

import java.util.Locale;

/**
 * Created by giovanni on 18/05/2017.
 */

public class GpsLocationRetriever implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, ActivityCompat.OnRequestPermissionsResultCallback {

    private Activity mActivity;
    private Context mActivityContext;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;

    final String [] mPermissions = new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION};
    final int mRequestCode = 1000;

    private Address mAddress;

    public GpsLocationRetriever(Activity activity, Context activityContext) {
        mActivityContext = activityContext;
        mActivity = activity;
        mAddress = new Address(Locale.ITALY);
        initGoogleApiClient();
    }

    private void initGoogleApiClient () {

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(mActivityContext)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    public void startConnection () {
        mGoogleApiClient.connect();
    }

    public void stopConnection () {
        mGoogleApiClient.disconnect();
    }

    public Address getmAddress() {
        return mAddress;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(mActivityContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(mActivityContext, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            Log.d("ERROR","No permission");
            ActivityCompat.requestPermissions(mActivity, mPermissions, mRequestCode);
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (mLastLocation != null) {

            mAddress.setLongitude(mLastLocation.getLongitude());
            mAddress.setLatitude(mLastLocation.getLatitude());

            Log.d("TEST",mLastLocation.getLatitude() + " " + mLastLocation.getLongitude());

        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }
}
