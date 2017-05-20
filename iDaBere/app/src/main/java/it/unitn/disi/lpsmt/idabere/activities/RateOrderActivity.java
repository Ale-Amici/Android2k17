package it.unitn.disi.lpsmt.idabere.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import it.unitn.disi.lpsmt.idabere.R;
import it.unitn.disi.lpsmt.idabere.adapters.RatingItemsListAdapter;

public class RateOrderActivity extends AppCompatActivity {


    private RatingItemsListAdapter ratingItemsListAdapter;
    private ListView ratingItemsList;


    // Fake Data
    ArrayList<String> listDataHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_order);

        initView();
        fillData();

        ratingItemsListAdapter = new RatingItemsListAdapter(this,R.layout.rate_order_list_item,listDataHeader);
        ratingItemsList.setAdapter(ratingItemsListAdapter);

    }

    private void initView () {
        ratingItemsList = (ListView) findViewById(R.id.items_list_view);
    }

    private void fillData() {
        listDataHeader = new ArrayList<>();

        // Adding child data
        for (int i = 0; i<10; i++) {

            listDataHeader.add(Integer.toString(i));

        }

    }
}
