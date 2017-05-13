package it.unitn.disi.lpsmt.idabere;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import it.unitn.disi.lpsmt.idabere.R;

public class GpsLoadingActivity extends AppCompatActivity {

    ProgressBar mLoadingIndicator;
    Context mContext;
    String [] fakeData = {"A"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps_loading);

        initViewComps();
        mContext = this;
        new LocationsLoader().execute(fakeData);

    }

    private void initViewComps () {

        mLoadingIndicator = (ProgressBar) findViewById(R.id.progressBar);

    }

    public class LocationsLoader extends AsyncTask<String[],String[],String[]> {


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
