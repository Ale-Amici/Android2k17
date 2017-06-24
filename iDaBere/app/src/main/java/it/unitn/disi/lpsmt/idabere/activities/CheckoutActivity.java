package it.unitn.disi.lpsmt.idabere.activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

import it.unitn.disi.lpsmt.idabere.R;
import it.unitn.disi.lpsmt.idabere.adapters.CheckoutExpandableListAdapter;
import it.unitn.disi.lpsmt.idabere.models.Order;
import it.unitn.disi.lpsmt.idabere.session.AppSession;
import it.unitn.disi.lpsmt.idabere.utils.AppStatus;


public class CheckoutActivity extends AppCompatActivity {

    private Context mContext;
    private ExpandableListView mCheckoutExpandableListView;
    private CheckoutExpandableListAdapter mCheckoutListAdapter;
    private TextView totalOrderInfo;
    private TextView deliveryType;
    private TextView paymentMethod;
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
                        if (isAuthenthicate() && AppStatus.getInstance(mContext).isOnline()){
                            intent.setClass(mContext,OrderStatusActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(mContext, "Nessuna connessione dati attiva", Toast.LENGTH_SHORT).show();
                        }
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
        totalOrderInfo.setText(new DecimalFormat("##0.00").format(currentOrder.getTotalPrice()));
        deliveryType.setText(currentOrder.getChosenDeliveryPlace().getName());
        getDeliveryTypeDetails.setText(currentOrder.getChosenDeliveryPlace().toString());
        if(currentOrder.isUsingCreditCard()){
            paymentMethod.setText(currentOrder.getChosenCreditCard().toString());
        }
        else{
            paymentMethod.setText("CONTANTI");
        }

        mCheckoutListAdapter.notifyDataSetChanged();
        super.onResume();
    }

    private void initViewComps () {
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.checkout_bottom_navigation);
        mCheckoutExpandableListView = (ExpandableListView) findViewById(R.id.checkout_list);
        totalOrderInfo = (TextView) findViewById(R.id.total_order_price);
        deliveryType = (TextView) findViewById(R.id.delivery_type_choosen);
        paymentMethod = (TextView) findViewById(R.id.payment_method_choosen);
        getDeliveryTypeDetails = (TextView) findViewById(R.id.delivery_type_value);
    }

    private boolean isAuthenthicate () {
        boolean result = false;
        if (AppSession.getInstance().getmCustomer().getId() != -1) {
            result = true;
        } else {
            // Start Login activity for authentication
            Intent loginIntent = new Intent();
            loginIntent.setClass(mContext, LoginActivity.class);
            startActivity(loginIntent);
        }
        return result;
    }

    private class SendOrderAsyncTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected void onPreExecute() {
            Order currentOrder = AppSession.getInstance().getmCustomer().getOrder();
            
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }
    }

}
