package it.unitn.disi.lpsmt.idabere.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SearchEvent;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Locale;

import it.unitn.disi.lpsmt.idabere.Models.Bar;
import it.unitn.disi.lpsmt.idabere.R;
import it.unitn.disi.lpsmt.idabere.adapters.BarsArrayAdapter;

public class ListBarActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private Context mContext;
    private ListView barsListView;
    private ArrayList<Bar> barsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_bar);

        initViewComps();
        mContext = this;

        barsList = new ArrayList<>();
        /****************** FAKE DATA ***/
        Bar tmpBar = new Bar();
        tmpBar.setName("Bel bar");
        barsList.add(tmpBar);
        tmpBar = new Bar();
        tmpBar.setName("Bel bar 2");
        barsList.add(tmpBar);

        tmpBar = new Bar();
        tmpBar.setName("Bellissimo bar");
        barsList.add(tmpBar);
        /********************************/
        barsListView = (ListView) this.findViewById(R.id.bars_list_view);
        barsListView.setAdapter(new BarsArrayAdapter(mContext,R.layout.bar_list_item,barsList));


//        // Get the intent, verify the action and get the query
//        Intent intent = getIntent();
//        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
//            String query = intent.getStringExtra(SearchManager.QUERY);
//            //doMySearch(query);
//        }
    }

    /** UI elements methods **/

    // Instantiate layout elements
    private void initViewComps () {
    }


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

        searchView.setSubmitButtonEnabled(false);
        searchView.setOnQueryTextListener(this);

        return super.onCreateOptionsMenu(menu);
    }

//    // Handle menu action buttons click
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        boolean result = false;
//        int itemId = item.getItemId();
//
//        switch (itemId) {
//            case R.id.action_search_bar_icon :
//                onSearchRequested();
//                result = true;
//                break;
//            case R.id.action_qr_scanner_icon :
//
//                Intent intent = new Intent();
//                intent.setClass(mContext,QrCodeScannerActivity.class);
//                startActivity(intent);
//
//                result = true;
//                break;
//            case R.id.action_clear_search_bar_icon :
//                result = true;
//                break;
//            default:
//                result = super.onOptionsItemSelected(item);
//        }
//
//        return result;
//    }

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

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        ((BarsArrayAdapter)barsListView.getAdapter()).getFilter().filter(newText);
        return true;
    }
}
