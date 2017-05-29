package it.unitn.disi.lpsmt.idabere.activities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Spinner;

import it.unitn.disi.lpsmt.idabere.R;

public class PaymentTypeActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationMenu;
    private Spinner creditCardSpinner;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_type);


        initViewComps();

        mContext = this;

        bottomNavigationMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                boolean result = false;
                int itemId = item.getItemId();
                Intent intent = new Intent();

                switch (itemId) {
                    case  R.id.navigation_delivery_type :
                        PaymentTypeActivity.super.onBackPressed();
                        result = true;
                        break;

                    case R.id.navigation_confirm_payment :
                        intent.setClass(mContext,CheckoutActivity.class);
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
        bottomNavigationMenu =  (BottomNavigationView) findViewById(R.id.payment_bottom_navigation);
        creditCardSpinner = (Spinner) findViewById(R.id.credit_card_drop_down);
    }

}
