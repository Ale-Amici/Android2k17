package it.unitn.disi.lpsmt.idabere.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import it.unitn.disi.lpsmt.idabere.R;
import it.unitn.disi.lpsmt.idabere.adapters.CheckoutListViewAdapter;
import it.unitn.disi.lpsmt.idabere.models.OrderItem;
import it.unitn.disi.lpsmt.idabere.session.AppSession;

public class CheckoutActivity extends AppCompatActivity {

    private Context mContext;
    private ListView mCheckoutListView;
    private CheckoutListViewAdapter mCheckoutListAdapter;

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        initViewComps();

        mContext = this;

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                boolean result = false;
                int itemId = item.getItemId();
                Intent intent = new Intent();

                switch (itemId) {
                    case R.id.navigation_payment_type:
                        CheckoutActivity.super.onBackPressed();
                        result = true;
                        break;

                    case R.id.navigation_confirm_payment:
                        intent.setClass(mContext,OrderStatusActivity.class);
                        startActivity(intent);
                        result = true;
                        break;
                }
                return result;
            }

        });

        mCheckoutListAdapter = new CheckoutListViewAdapter(this);
        mCheckoutListView.setAdapter(mCheckoutListAdapter);
        fillData();
    }

    private void initViewComps () {
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.checkout_bottom_navigation);
        mCheckoutListView = (ListView) findViewById(R.id.checkout_list);
    }

//    TODO Integrare con un adapter piu' strutturato
    private void fillData () {
        ArrayList<OrderItem> orderItems = AppSession.getInstance().getmCustomer().getOrder().getOrderItems();
        for (OrderItem item : orderItems) {
            mCheckoutListAdapter.addSectionHeaderItem(item.getBarMenuItem().getCategory());
            mCheckoutListAdapter.addItem(item.getBarMenuItem().getName());
        }
    }

}
