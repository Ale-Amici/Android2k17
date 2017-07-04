package it.unitn.disi.lpsmt.idabere.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import it.unitn.disi.lpsmt.idabere.R;
import it.unitn.disi.lpsmt.idabere.adapters.ReviewOrderExpandableListAdapter;
import it.unitn.disi.lpsmt.idabere.models.BarMenu;
import it.unitn.disi.lpsmt.idabere.session.AppSession;

public class ReviewOrderActivity extends AppCompatActivity {

    private ExpandableListView reviewExpandableList;
    private BottomNavigationView bottomNavigationMenu;
    private TextView totalPriceTV;
    private TextView totalItemsTV;
    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_order);

        initViewComps();

        mContext = this;

        BarMenu barMenuForReview = AppSession.getInstance().getmCustomer().getOrder().getOrderMenuForReview();
        // setting list adapter
        reviewExpandableList.setAdapter(new ReviewOrderExpandableListAdapter(mContext,barMenuForReview, totalPriceTV, totalItemsTV, reviewExpandableList));

        bottomNavigationMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                boolean result = false;
                int itemId = item.getItemId();
                Intent intent = new Intent();

                switch (itemId) {
                    case  R.id.navigation_menu_list :
                        ReviewOrderActivity.super.onBackPressed();
                        result = true;
                        break;

                    case R.id.navigation_delivery_type :
                        if (AppSession.getInstance().getmCustomer().getOrder().getOrderItems().size() > 0){
                            intent.setClass(mContext,DeliveryPlaceActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(mContext, "Devi prima effettuare almeno una scelta", Toast.LENGTH_SHORT).show();
                        }
                        result = true;
                        break;
                }
                return result;
            }
        });

    }

    // Instantiate layout elements
    private void initViewComps () {
        // get the listview
        reviewExpandableList = (ExpandableListView) findViewById(R.id.expandable_review_list);
        // get the bottom navigation menu
        bottomNavigationMenu =  (BottomNavigationView) findViewById(R.id.review_bottom_navigation);

        totalPriceTV = (TextView) findViewById(R.id.total_order_price);
        totalItemsTV = (TextView) findViewById(R.id.total_order_items);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ReviewOrderExpandableListAdapter reviewAdapter = (ReviewOrderExpandableListAdapter) reviewExpandableList.getExpandableListAdapter();
        AddChoiceActivity.checkNewChoiceResult(requestCode, resultCode, data, mContext, reviewAdapter);
    }



}
