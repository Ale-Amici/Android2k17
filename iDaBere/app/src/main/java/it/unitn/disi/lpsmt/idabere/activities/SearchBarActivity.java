package it.unitn.disi.lpsmt.idabere.activities;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SearchEvent;
import android.view.View;

import java.util.ArrayList;

import it.unitn.disi.lpsmt.idabere.DAOIntefaces.FactoryDAO;
import it.unitn.disi.lpsmt.idabere.DAOInterfacesImpl.FactoryDAOImpl;
import it.unitn.disi.lpsmt.idabere.Models.Bar;
import it.unitn.disi.lpsmt.idabere.R;
import it.unitn.disi.lpsmt.idabere.utils.GpsLocationRetriever;

public class SearchBarActivity extends AppCompatActivity {

    private final String TAG = "SeachBarActivity";

    private Context mContext;
    public static FactoryDAO factoryDAO = new FactoryDAOImpl();

    private GpsLocationRetriever gpsLocationRetriever;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_bar);

        initViewComps();
        mContext = this;

        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //doMySearch(query);
        }

        gpsLocationRetriever = new GpsLocationRetriever(this, this);

        new GpsLoader().execute();


    }

    private void updateCoordinates() {
        new GpsLoader().execute();
    }


    private class GpsLoader extends AsyncTask<Address, Void, ArrayList<Bar>> {
        @Override
        protected void onPreExecute() {
            gpsLocationRetriever.startConnection();
            super.onPreExecute();
        }

        @Override
        protected ArrayList<Bar> doInBackground(Address... params) {

            ArrayList<Bar> bars = null;

            while (gpsLocationRetriever.getmRequestingLocationUpdates()) {
                //Log.d("WAIT", "Waiting...");
            }

            Log.d(TAG, "DONE");

            if (gpsLocationRetriever.isCoordinatesRetrieveSuccess()) {
                bars = new ArrayList<>();
                Log.d("LOCATION", gpsLocationRetriever.getmAddress().toString());
                bars = SearchBarActivity.factoryDAO.newBarsDAO().getBarsByCoordinates(gpsLocationRetriever.getmAddress());
            }

            return bars;
        }

        @Override
        protected void onPostExecute(ArrayList<Bar> bars) {


            //Log.d("BARS",bars.toString());
            super.onPostExecute(bars);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        final int REQUEST_CHECK_SETTINGS_FLAG = 0x1;
        //REQUEST_CHECK_SETTINGS_FLAG = gpsLocationRetriever.getREQUEST_CHECK_SETTINGS();;

        switch (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().

            case REQUEST_CHECK_SETTINGS_FLAG:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        Log.i(TAG, "User agreed to make required location settings changes.");
                        // Nothing to do. startLocationupdates() gets called in onResume again.
                        break;
                    case Activity.RESULT_CANCELED:
                        Log.i(TAG, "User chose not to make required location settings changes.");
                        gpsLocationRetriever.stopConnection();
                        break;
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        gpsLocationRetriever.startConnection();
        super.onResume();
    }

    /** UI elements methods **/

    // Instantiate layout elements
    private void initViewComps () {}


    /** Menu bar methods **/

    // Inflate the menu into the activity layout
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_bar_menu, menu);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search_bar_icon).getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return super.onCreateOptionsMenu(menu);
    }

    // Handle menu action buttons click
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean result = false;
        int itemId = item.getItemId();

        switch (itemId) {
            case R.id.action_search_bar_icon :
                onSearchRequested();
                result = true;
                break;
            case R.id.action_qr_scanner_icon :

                Intent intent = new Intent();
                intent.setClass(mContext,QrCodeScannerActivity.class);
                startActivity(intent);

                result = true;
                break;
            case R.id.action_clear_search_bar_icon :
                result = true;
                break;
            case R.id.update_coordinates :
                updateCoordinates();
                break;
            default:
                result = super.onOptionsItemSelected(item);
        }

        return result;
    }

    /** Class methods bar methods **/

    // This hook is called when the user signals the desire to start a search.
    public boolean onSearchRequested(SearchEvent seachEvent) {
        boolean result = false;
        return result;
    }

    public void goToMenuTest (View v) {
        Intent intent = new Intent();
        intent.setClass(mContext,MenuActivity.class);
        startActivity(intent);
    }

}
