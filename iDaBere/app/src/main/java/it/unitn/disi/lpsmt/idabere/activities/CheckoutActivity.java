package it.unitn.disi.lpsmt.idabere.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.google.android.gms.vision.text.Text;

import it.unitn.disi.lpsmt.idabere.R;
import it.unitn.disi.lpsmt.idabere.adapters.CheckoutExpandableListAdapter;
import it.unitn.disi.lpsmt.idabere.models.Order;
import it.unitn.disi.lpsmt.idabere.session.AppSession;


public class CheckoutActivity extends AppCompatActivity {

    private Context mContext;
    private ExpandableListView mCheckoutExpandableListView;
    private CheckoutExpandableListAdapter mCheckoutListAdapter;
    private TextView totalOrderInfo;
    private TextView deliveryType;
    private TextView getDeliveryTypeDetails;

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

        mCheckoutListAdapter = new CheckoutExpandableListAdapter(this, AppSession.getInstance().getmCustomer().getOrder());
        mCheckoutExpandableListView.setAdapter(mCheckoutListAdapter);

    }

    @Override
    protected void onResume() {
        Order currentOrder = AppSession.getInstance().getmCustomer().getOrder();
        totalOrderInfo.setText(Double.toString(currentOrder.getTotalPrice()));
        mCheckoutListAdapter.notifyDataSetChanged();
        super.onResume();
    }

    private void initViewComps () {
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.checkout_bottom_navigation);
        mCheckoutExpandableListView = (ExpandableListView) findViewById(R.id.checkout_list);
        totalOrderInfo = (TextView) findViewById(R.id.total_order_price);
        deliveryType = (TextView) findViewById(R.id.delivery_type_choosen);
        getDeliveryTypeDetails = (TextView) findViewById(R.id.delivery_type_value);
    }

}
