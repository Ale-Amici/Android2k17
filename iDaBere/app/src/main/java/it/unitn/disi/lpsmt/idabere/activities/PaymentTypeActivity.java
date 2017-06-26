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
import it.unitn.disi.lpsmt.idabere.models.Customer;
import it.unitn.disi.lpsmt.idabere.models.DeliveryPlace;
import it.unitn.disi.lpsmt.idabere.models.Order;
import it.unitn.disi.lpsmt.idabere.models.CreditCard;
import it.unitn.disi.lpsmt.idabere.session.AppSession;

public class PaymentTypeActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    private BottomNavigationView bottomNavigationMenu;

    private Spinner creditCardSpinner;

    private TextView totalOrderInfo;

    private ArrayAdapter<CreditCard> creditCardSpinnerAdapter;

    private RadioGroup paymentsRadioGroup;

    private RadioButton firstChoiceRadioButton;
    private RadioButton secondChoiceRadioButton;

    private LinearLayout firstChoiceLayout;

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
                        returnDeliveryIfLogged(intent);
                        result = true;
                        break;

                    case R.id.navigation_confirm_payment :
                        if (checkSelection()){
                            intent.setClass(mContext,CheckoutActivity.class);
                            startActivity(intent);
                            result = true;
                        }

                        break;
                }
                return result;
            }
        });

        creditCardSpinnerAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,AppSession.getInstance().getmCustomer().getCreditCards());
        creditCardSpinner.setAdapter(creditCardSpinnerAdapter);

        // Disable/enable spinner based on radio button clicked
        paymentsRadioGroup.clearCheck();
        enableRadioButtonDetails(null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Customer currentCustomer = AppSession.getInstance().getmCustomer();
        Order currentOrder = AppSession.getInstance().getmCustomer().getOrder();
        CreditCard currentCreditCard = currentOrder.getChosenCreditCard();
        if(currentOrder.isUsingCreditCard()){
            firstChoiceRadioButton.setChecked(true);
            enableRadioButtonDetails(firstChoiceLayout);
            creditCardSpinner.setSelection(currentCustomer.getCreditCards().indexOf(currentCreditCard));
        }
        else{
            secondChoiceRadioButton.setChecked(true);
        }
        totalOrderInfo.setText(new DecimalFormat("##0.00").format(currentOrder.getTotalPrice()));
        paymentsRadioGroup.setOnCheckedChangeListener(this);

    }

    @Override
    public void onBackPressed() {
        if (AppSession.getInstance().getmCustomer().getId() != -1){
            Intent intent = new Intent();
            returnDeliveryIfLogged(intent);
        } else {
            super.onBackPressed();
        }
    }

    public boolean checkSelection () {
        if(savePaymentMethod()){
            return true;
        }
        else{
            Toast.makeText(mContext, "Devi effettuare una scelta", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private void returnDeliveryIfLogged (Intent intent) {
        intent.setClass(mContext, DeliveryPlaceActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private boolean savePaymentMethod(){
        boolean result = false;
        int radioButtonId = paymentsRadioGroup.getCheckedRadioButtonId();
        Order sessionOrder = AppSession.getInstance().getmCustomer().getOrder();
        if(radioButtonId != -1){
            switch (radioButtonId) {
                case R.id.first_choice_radiobutton :
                    sessionOrder.setUsingCreditCard(true);
                    sessionOrder.setChosenCreditCard((CreditCard) creditCardSpinner.getSelectedItem());
                    break;
                case R.id.second_choice_radiobutton :
                    sessionOrder.setUsingCreditCard(false);
                    sessionOrder.setChosenCreditCard(null);
                    break;
            }
            Log.d("CHOICE", "Choice (if null -> Cash): "+AppSession.getInstance().getmCustomer().getOrder().getChosenCreditCard());
            result = true;
        }
        return result;
    }

    private void setEnabledOnRadioButtonDetails(LinearLayout layout, boolean enabled){
        for (int i = 0; i < layout.getChildCount(); i++) {
            View child = layout.getChildAt(i);
            child.setEnabled(enabled);
        }
    }

    private void enableRadioButtonDetails(LinearLayout layout) {
        if(layout == null){
            setEnabledOnRadioButtonDetails(firstChoiceLayout, false);
        }
        else{
            setEnabledOnRadioButtonDetails(layout, true);
        }
    }

    // Instantiate layout elements
    private void initViewComps () {
        // get the bottom navigation menu
        bottomNavigationMenu =  (BottomNavigationView) findViewById(R.id.payment_bottom_navigation);
        creditCardSpinner = (Spinner) findViewById(R.id.credit_card_drop_down);
        creditCardSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                savePaymentMethod();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                savePaymentMethod();
            }
        });
        totalOrderInfo = (TextView) findViewById(R.id.payment_total_order_price);
        paymentsRadioGroup = (RadioGroup) findViewById(R.id.payments_radiogroup);

        firstChoiceRadioButton = (RadioButton) findViewById(R.id.first_choice_radiobutton);
        secondChoiceRadioButton = (RadioButton) findViewById(R.id.second_choice_radiobutton);

        firstChoiceLayout = (LinearLayout) findViewById(R.id.first_choice_layout);

    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (group.getCheckedRadioButtonId()) {
            case R.id.first_choice_radiobutton :
                enableRadioButtonDetails(firstChoiceLayout);
                break;
            case R.id.second_choice_radiobutton :
                break;
        }
        savePaymentMethod();
    }
}
