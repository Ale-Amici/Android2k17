package it.unitn.disi.lpsmt.idabere;

import android.app.SearchManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import it.unitn.disi.lpsmt.idabere.adapters.MenuCategoryExpandableListAdapter;
import it.unitn.disi.lpsmt.idabere.adapters.ReviewOrderExpandableListAdapter;

public class ReviewOrderActivity extends AppCompatActivity {

    private ReviewOrderExpandableListAdapter reviewOrderExpandableListAdapter;
    private ExpandableListView reviewExpandableList;

    // Fake Data
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_order);

        initViewComps();
        // preparing fake list data
        prepareListData();

        reviewOrderExpandableListAdapter = new ReviewOrderExpandableListAdapter (this, listDataHeader, listDataChild);

        // setting list adapter
        reviewExpandableList.setAdapter(reviewOrderExpandableListAdapter);

    }

    // Instantiate layout elements
    private void initViewComps () {
        // get the listview
        reviewExpandableList = (ExpandableListView) findViewById(R.id.expandable_review_list);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.item_list_menu,menu);


        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search_bar_icon).getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));


        return super.onCreateOptionsMenu(menu);

    }

    /*
         * Preparing the list data
         */
    private void prepareListData() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Top 250");
        listDataHeader.add("Now Showing");
        listDataHeader.add("Coming Soon..");

        // Adding child data
        List<String> top250 = new ArrayList<String>();
        top250.add("The Shawshank Redemption");
        top250.add("The Godfather");
        top250.add("The Godfather: Part II");
        top250.add("Pulp Fiction");
        top250.add("The Good, the Bad and the Ugly");
        top250.add("The Dark Knight");
        top250.add("12 Angry Men");

        List<String> nowShowing = new ArrayList<String>();
        nowShowing.add("The Conjuring");
        nowShowing.add("Despicable Me 2");
        nowShowing.add("Turbo");
        nowShowing.add("Grown Ups 2");
        nowShowing.add("Red 2");
        nowShowing.add("The Wolverine");

        List<String> comingSoon = new ArrayList<String>();
        comingSoon.add("2 Guns");
        comingSoon.add("The Smurfs 2");
        comingSoon.add("The Spectacular Now");
        comingSoon.add("The Canyons");
        comingSoon.add("Europa Report");

        listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
        listDataChild.put(listDataHeader.get(1), nowShowing);
        listDataChild.put(listDataHeader.get(2), comingSoon);
    }
}