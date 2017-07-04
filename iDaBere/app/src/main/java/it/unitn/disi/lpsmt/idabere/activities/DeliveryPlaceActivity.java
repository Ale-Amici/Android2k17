package it.unitn.disi.lpsmt.idabere.activities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;

import it.unitn.disi.lpsmt.idabere.R;
import it.unitn.disi.lpsmt.idabere.models.BarCounter;
import it.unitn.disi.lpsmt.idabere.models.DeliveryPlace;
import it.unitn.disi.lpsmt.idabere.models.Order;
import it.unitn.disi.lpsmt.idabere.session.AppSession;

public class DeliveryPlaceActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    private final int LOGIN_REQUEST_CODE = 10;

    private BottomNavigationView bottomNavigationMenu;
    private Spinner countersSpinner;
    private Spinner tablesSpinner;

    private TextView totalOrderInfo;
    private TextView totalItemsTV;


    private RadioGroup deliveriesRadioGroup;

    private RadioButton firstChoiceRadioButton;
    private RadioButton secondChoiceRadioButton;

    private LinearLayout firstChoiceLayout;
    private LinearLayout secondChoideLayout;

    private ArrayAdapter counterSpinnerAdapter;
    private ArrayAdapter tableSpinnerAdapter;

    private ArrayList<DeliveryPlace> mBarCounters;
    private ArrayList<DeliveryPlace> mBarTables;

    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_place);

        initViewComps();
        setTablesAndCounters();

        mContext = this;



        // Set the bottom navigation menu

        bottomNavigationMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                boolean result = false;
                int itemId = item.getItemId();
                Intent intent = new Intent();

                switch (itemId) {
                    case R.id.navigation_review_order:
                        DeliveryPlaceActivity.super.onBackPressed();
                        result = true;
                        break;

                    case R.id.navigation_payment_type:
                        if (checkSelection() && isAuthenthicate()) {
                            intent.setClass(mContext, PaymentTypeActivity.class);
                            startActivity(intent);
                            result = true;
                        }
                        break;
                }
                return result;
            }
        });

        // Set spinners adapters

        counterSpinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mBarCounters);
        countersSpinner.setAdapter(counterSpinnerAdapter);

        tableSpinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mBarTables);
        tablesSpinner.setAdapter(tableSpinnerAdapter);


        // Disable/enable spinner based on radio button clicked
        deliveriesRadioGroup.clearCheck();
        enableRadioButtonDetails(null);;


    }

    @Override
    protected void onResume() {
        super.onResume();
        setTablesAndCounters();
        Order currentOrder = AppSession.getInstance().getmCustomer().getOrder();
        DeliveryPlace currentDeliveryPlace = currentOrder.getChosenDeliveryPlace();
        if (currentDeliveryPlace != null) {
            if (currentDeliveryPlace instanceof BarCounter) {
                firstChoiceRadioButton.setChecked(true);
                countersSpinner.setSelection(mBarCounters.indexOf(currentDeliveryPlace));
                enableRadioButtonDetails(firstChoiceLayout);
            } else {
                secondChoiceRadioButton.setChecked(true);
                tablesSpinner.setSelection(mBarTables.indexOf(currentDeliveryPlace));
                enableRadioButtonDetails(secondChoideLayout);
            }
        }
        totalItemsTV.setText(Integer.toString(currentOrder.getTotalQuantity()));
        totalOrderInfo.setText(new DecimalFormat("##0.00").format(currentOrder.getTotalPrice()));
        deliveriesRadioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Intent intent = new Intent();
        switch (resultCode) {
            case RESULT_OK :
                intent.setClass(this, PaymentTypeActivity.class);
                startActivity(intent);
                break;
            default:
                Toast.makeText(this, getResources().getString(R.string.authentication_failed_message), Toast.LENGTH_SHORT).show();
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public boolean checkSelection() {
        if(saveDeliveryChoice()){
            return true;
        }else {
            Toast.makeText(mContext, "Devi effettuare una scelta", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private void setEnabledOnRadioButtonDetails(LinearLayout layout, boolean enabled){
        for (int i = 0; i < layout.getChildCount(); i++) {
            View child = layout.getChildAt(i);
            child.setEnabled(enabled);
        }
    }
    private void enableRadioButtonDetails(LinearLayout layout) {
        if(layout == null){ //disable all details
            setEnabledOnRadioButtonDetails(firstChoiceLayout, false);
            setEnabledOnRadioButtonDetails(secondChoideLayout, false);
        }else{
            setEnabledOnRadioButtonDetails(layout, true);//enable the selected details
            LinearLayout oldEnabledLL = (firstChoiceLayout == layout)? secondChoideLayout : firstChoiceLayout;
            setEnabledOnRadioButtonDetails(oldEnabledLL, false);//disable the other details
        }

    }

    // Instantiate layout elements
    private void initViewComps() {
        // get the bottom navigation menu
        bottomNavigationMenu = (BottomNavigationView) findViewById(R.id.delivery_bottom_navigation);

        tablesSpinner = (Spinner) findViewById(R.id.tables_drop_down);
        tablesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                saveDeliveryChoice();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                saveDeliveryChoice();
            }
        });

        countersSpinner = (Spinner) findViewById(R.id.counters_drop_down);
        countersSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                saveDeliveryChoice();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                saveDeliveryChoice();
            }
        });

        totalOrderInfo = (TextView) findViewById(R.id.total_order_price);
        totalItemsTV = (TextView) findViewById(R.id.total_order_items);

        deliveriesRadioGroup = (RadioGroup) findViewById(R.id.deliveries_radio_group);

        firstChoiceRadioButton = (RadioButton) findViewById(R.id.first_delivery_choice);
        secondChoiceRadioButton = (RadioButton) findViewById(R.id.second_delivery_choice);

        firstChoiceLayout = (LinearLayout) findViewById(R.id.first_delivery_choice_layout);
        secondChoideLayout = (LinearLayout) findViewById(R.id.second_delivery_choice_layout);

        firstChoiceRadioButton.setId(R.id.first_choice_radiobutton);
        /*firstChoiceRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                toggleRadioButtonDetails(firstChoiceLayout);
            }
        });*/
        secondChoiceRadioButton.setId(R.id.second_choice_radiobutton);
        /*secondChoiceRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                toggleRadioButtonDetails(secondChoideLayout);
            }
        });*/

    }

    private boolean saveDeliveryChoice(){
        boolean result = false;
        Order sessionOrder = AppSession.getInstance().getmCustomer().getOrder();
        int radioButtonId = deliveriesRadioGroup.getCheckedRadioButtonId();

        if (radioButtonId != -1) {
            switch (radioButtonId) {
                case R.id.first_choice_radiobutton :
                    sessionOrder.setChosenDeliveryPlace( (DeliveryPlace) countersSpinner.getSelectedItem());
                    break;
                case R.id.second_choice_radiobutton :
                    sessionOrder.setChosenDeliveryPlace( (DeliveryPlace) tablesSpinner.getSelectedItem());
                    break;
            }
            Log.d("DELIVERY CHOICE", AppSession.getInstance().getmCustomer().getOrder().getChosenDeliveryPlace().toString());
            result = true;
        }
        return result;
    }

    private void setTablesAndCounters(){
        mBarCounters = new ArrayList<>();
        mBarTables = new ArrayList<>();
        for(DeliveryPlace deliveryPlace: AppSession.getInstance().getmBar().getDeliveryPlaces()){
            if(deliveryPlace instanceof BarCounter){
                mBarCounters.add(deliveryPlace);
            }
            else{
                mBarTables.add(deliveryPlace);
            }
        }
    }


    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (group.getCheckedRadioButtonId()) {
            case R.id.first_choice_radiobutton :
                enableRadioButtonDetails(firstChoiceLayout);
                break;
            case R.id.second_choice_radiobutton :
                enableRadioButtonDetails(secondChoideLayout);
                break;
        }
        saveDeliveryChoice();
    }

    private boolean isAuthenthicate () {
        boolean result = false;
        if (AppSession.getInstance().getmCustomer().getId() != -1) {
            result = true;
        } else {
            // Start Login activity for authentication
            Intent loginIntent = new Intent();
            loginIntent.setClass(mContext, LoginActivity.class);
            startActivityForResult(loginIntent, LoginActivity.IS_LOGGED_REQUEST_CODE);
        }
        return result;
    }

}
