package it.unitn.disi.lpsmt.idabere.activities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import it.unitn.disi.lpsmt.idabere.R;

public class PaymentTypeActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationMenu;
    private Spinner creditCardSpinner;

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

        // TODO Creare l'adapter per inserire i dati nel dropdown

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
        secondChoiceRadioButton = (RadioButton) findViewById(R.id.second_choice_radiobutton);

        firstChoiceLayout = (LinearLayout) findViewById(R.id.first_choice_layout);

    }

}
