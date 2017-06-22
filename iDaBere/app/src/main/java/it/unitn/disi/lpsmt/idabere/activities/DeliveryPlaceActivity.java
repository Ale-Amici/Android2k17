package it.unitn.disi.lpsmt.idabere.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.IntegerRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
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
import it.unitn.disi.lpsmt.idabere.models.PaymentMethod;
import it.unitn.disi.lpsmt.idabere.models.Table;
import it.unitn.disi.lpsmt.idabere.session.AppSession;

public class DeliveryPlaceActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private final int LOGIN_REQUEST_CODE = 10;

    private BottomNavigationView bottomNavigationMenu;
    private Spinner countersSpinner;
    private Spinner tablesSpinner;
    private TextView totalOrderInfo;

    private RadioGroup deliveriesRadioGroup;

    private RadioButton firstChoiceRadioButton;
    private RadioButton secondChoiceRadioButton;

    private LinearLayout firstChoiceLayout;
    private LinearLayout secondChoideLayout;

    private ArrayAdapter counterSpinnerAdapter;
    private ArrayAdapter tableSpinnerAdapter;

    private Context mContext;


    // FAKE DATA
    ArrayList<String> tables = new ArrayList<>();
    ArrayList<String> counters = new ArrayList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_place);

        initViewComps();

        mContext = this;

        // Init fake data
        for (int i = 1; i < 11; i++) {
            tables.add(Integer.toString(i));
            counters.add("Piano "+Integer.toString(i));
        }


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

        counterSpinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, counters);
        countersSpinner.setAdapter(counterSpinnerAdapter);

        tableSpinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, tables);
        tablesSpinner.setAdapter(tableSpinnerAdapter);


        // Disable/enable spinner based on radio button clicked
        deliveriesRadioGroup.clearCheck();
        toggleRadioButtonDetails(firstChoiceLayout);
        toggleRadioButtonDetails(secondChoideLayout);


    }

    @Override
    protected void onResume() {
        Order currentOrder = AppSession.getInstance().getmCustomer().getOrder();
        DeliveryPlace currentDeliveryPlace = currentOrder.getChoosenDeliveryPlace();
        if (currentDeliveryPlace != null) {
            if (currentDeliveryPlace instanceof BarCounter) {
                firstChoiceRadioButton.toggle();
                countersSpinner.setSelection(counters.indexOf(currentDeliveryPlace.toString()));
            } else {
                secondChoiceRadioButton.toggle();
                tablesSpinner.setSelection(tables.indexOf( (currentDeliveryPlace).toString()) );
            }
        }
        totalOrderInfo.setText(new DecimalFormat("##0.00").format(currentOrder.getTotalPrice()));
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case RESULT_OK :
                Toast.makeText(this, "Autenticazione effettuata", Toast.LENGTH_LONG).show();
                break;
            default:
                Toast.makeText(this, "Autenticazione non effettuata", Toast.LENGTH_LONG).show();
                break;
        }
    }

    public boolean checkSelection() {
        boolean result = false;

        DeliveryPlace choosenDeliveryPlace = null;
        int radioButtonId = deliveriesRadioGroup.getCheckedRadioButtonId();

        if (radioButtonId != -1) {
            switch (radioButtonId) {
                case R.id.first_delivery_choice :
                    choosenDeliveryPlace = new BarCounter();
                    ((BarCounter) choosenDeliveryPlace).setCounterName((String)countersSpinner.getSelectedItem());

                    break;
                case R.id.second_delivery_choice :
                    choosenDeliveryPlace = new Table();
                    ((Table) choosenDeliveryPlace).setTableNumber(Integer.parseInt((String)tablesSpinner.getSelectedItem()));
                    break;
            }
            AppSession.getInstance().getmCustomer().getOrder().setChoosenDeliveryPlace(choosenDeliveryPlace);
            Log.d("DELIVERY CHOICE", AppSession.getInstance().getmCustomer().getOrder().getChoosenDeliveryPlace().toString());
            result = true;
        } else {
            Toast.makeText(mContext, "Devi effettuare una scelta", Toast.LENGTH_SHORT).show();
        }

        return result;
    }

    private void toggleRadioButtonDetails(LinearLayout layout) {
        for (int i = 0; i < layout.getChildCount(); i++) {
            View child = layout.getChildAt(i);
            if (child.isEnabled()) {
                child.setEnabled(false);
            } else {
                child.setEnabled(true);
            }
        }
    }

    // Instantiate layout elements
    private void initViewComps() {
        // get the bottom navigation menu
        bottomNavigationMenu = (BottomNavigationView) findViewById(R.id.delivery_bottom_navigation);

        tablesSpinner = (Spinner) findViewById(R.id.tables_drop_down);
        countersSpinner = (Spinner) findViewById(R.id.counters_drop_down);
        totalOrderInfo = (TextView) findViewById(R.id.delivery_total_order_price);

        deliveriesRadioGroup = (RadioGroup) findViewById(R.id.deliveries_radio_group);

        firstChoiceRadioButton = (RadioButton) findViewById(R.id.first_delivery_choice);
        secondChoiceRadioButton = (RadioButton) findViewById(R.id.second_delivery_choice);

        firstChoiceLayout = (LinearLayout) findViewById(R.id.first_delivery_choice_layout);
        secondChoideLayout = (LinearLayout) findViewById(R.id.second_delivery_choice_layout);

        firstChoiceRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                toggleRadioButtonDetails(firstChoiceLayout);
            }
        });

        secondChoiceRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                toggleRadioButtonDetails(secondChoideLayout);
            }
        });

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.first_delivery_choice :
                toggleRadioButtonDetails(firstChoiceLayout);
                break;
            case R.id.second_delivery_choice :
                toggleRadioButtonDetails(secondChoideLayout);
                break;
        }
    }

    private boolean isAuthenthicate () {
        boolean result = false;
        if (AppSession.getInstance().getmCustomer().getId() != -1) {
            result = true;
        } else {
            // Start Login activity for authentication
            Intent loginIntent = new Intent();
            loginIntent.setClass(mContext, LoginActivity.class);
            startActivityForResult(loginIntent, LOGIN_REQUEST_CODE);
        }
        return result;
    }

}
