package it.unitn.disi.lpsmt.idabere.activities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import it.unitn.disi.lpsmt.idabere.R;

public class DeliveryPlaceActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationMenu;
    private Spinner countersSpinner;
    private Spinner tablesSpinner;

    private ArrayAdapter counterSpinnerAdapter;
    private ArrayAdapter tableSpinnerAdapter;

    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_place);

        initViewComps();

        mContext = this;

        bottomNavigationMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                boolean result = false;
                int itemId = item.getItemId();
                Intent intent = new Intent();

                switch (itemId) {
                    case  R.id.navigation_review_order :
                        DeliveryPlaceActivity.super.onBackPressed();
                        //intent.setClass(mContext,ReviewOrderActivity.class);
                        //startActivity(intent);
                        result = true;
                        break;

                    case R.id.navigation_payment_type :
                        intent.setClass(mContext,PaymentTypeActivity.class);
                        startActivity(intent);
                        result = true;
                        break;
                }
                return result;
            }
        });



    }


    // Instantiate layout elements
    private void initViewComps () {
        // get the bottom navigation menu
        bottomNavigationMenu =  (BottomNavigationView) findViewById(R.id.delivery_bottom_navigation);

        tablesSpinner = (Spinner) findViewById(R.id.tables_drop_down);
        countersSpinner = (Spinner) findViewById(R.id.counters_drop_down);
    }

}
