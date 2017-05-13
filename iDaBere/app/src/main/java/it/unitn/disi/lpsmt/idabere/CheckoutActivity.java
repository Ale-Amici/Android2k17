package it.unitn.disi.lpsmt.idabere;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class CheckoutActivity extends AppCompatActivity {

    private TextView mTextMessage;

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        //mTextMessage = (TextView) findViewById(R.id.message);
        initComps();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_payment_type:
                        CheckoutActivity.super.onBackPressed();
                        return true;

                    case R.id.navigation_confirm_payment:

                        return true;
                }
                return false;
            }

        });
    }

    private void initComps () {
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.checkout_bottom_navigation);
    }

}
