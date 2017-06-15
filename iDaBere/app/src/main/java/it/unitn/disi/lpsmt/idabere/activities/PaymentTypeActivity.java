package it.unitn.disi.lpsmt.idabere.activities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import it.unitn.disi.lpsmt.idabere.R;
import it.unitn.disi.lpsmt.idabere.models.PaymentMethod;
import it.unitn.disi.lpsmt.idabere.session.AppSession;

public class PaymentTypeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private BottomNavigationView bottomNavigationMenu;

    private Spinner creditCardSpinner;

    private ArrayAdapter<PaymentMethod> creditCardSpinnerAdapter;

    private RadioGroup paymentsRadioGroup;

    private RadioButton firstChoiceRadioButton;

    private LinearLayout firstChoiceLayout;

    private Context mContext;

    // Fake data
    ArrayList<PaymentMethod> creditCards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_type);


        // Set fake datas
        creditCards = new ArrayList<>();
        PaymentMethod creditCardOne = new PaymentMethod();
        PaymentMethod creditCardTwo = new PaymentMethod();
        creditCardOne.setName("Mastercard");
        creditCardTwo.setName("Paypal");
        creditCards.add(creditCardOne);
        creditCards.add(creditCardTwo);
        AppSession.getInstance().getmCustomer().setPaymentMethods(creditCards);

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

        // Disable/enable spinner based on radio button clicked

        paymentsRadioGroup.clearCheck();
        toggleRadioButtonDetails(firstChoiceLayout);


        firstChoiceRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                toggleRadioButtonDetails(firstChoiceLayout);
            }
        });


        creditCardSpinnerAdapter = new ArrayAdapter<PaymentMethod>(this,android.R.layout.simple_list_item_1,AppSession.getInstance().getmCustomer().getPaymentMethods());
        creditCardSpinner.setAdapter(creditCardSpinnerAdapter);
    }

    public boolean checkSelection () {
        boolean result = false;

        if(paymentsRadioGroup.getCheckedRadioButtonId() != -1){
            result = true;
        } else {
            Toast.makeText(mContext, "Devi effettuare una scelta", Toast.LENGTH_SHORT).show();
        }

        return result;
    }

    private void toggleRadioButtonDetails(LinearLayout layout) {
        for (int i = 0; i < layout.getChildCount(); i++) {
            View child = layout.getChildAt(i);
            if(child.isEnabled()) {
                child.setEnabled(false);
            } else {
                child.setEnabled(true);
            }
        }
    }

    // Instantiate layout elements
    private void initViewComps () {
        // get the bottom navigation menu
        bottomNavigationMenu =  (BottomNavigationView) findViewById(R.id.payment_bottom_navigation);
        creditCardSpinner = (Spinner) findViewById(R.id.credit_card_drop_down);

        paymentsRadioGroup = (RadioGroup) findViewById(R.id.payments_radiogroup);

        firstChoiceRadioButton = (RadioButton) findViewById(R.id.first_choice_radiobutton);


        firstChoiceLayout = (LinearLayout) findViewById(R.id.first_choice_layout);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
