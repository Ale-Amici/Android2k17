package it.unitn.disi.lpsmt.idabere;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ExpandableListView;

public class DeliveryPlaceActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationMenu;

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
                    case  R.id.review_bottom_navigation :
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
    }

}
