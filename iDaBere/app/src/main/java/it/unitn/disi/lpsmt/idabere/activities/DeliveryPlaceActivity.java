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

import com.google.android.gms.vision.text.Line;

import it.unitn.disi.lpsmt.idabere.R;
import it.unitn.disi.lpsmt.idabere.models.DeliveryPlace;

public class DeliveryPlaceActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationMenu;
    private Spinner countersSpinner;
    private Spinner tablesSpinner;

    private RadioGroup deliveriesRadioGroup;

    private RadioButton firstChoiceRadioButton;
    private RadioButton secondChoiceRadioButton;

    private LinearLayout firstChoiceLayout;
    private LinearLayout secondChoideLayout;

    private ArrayAdapter counterSpinnerAdapter;
    private ArrayAdapter tableSpinnerAdapter;

    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_place);

        initViewComps();

        mContext = this;

        // Set the bottom navigation menu

        bottomNavigationMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                boolean result = false;
                int itemId = item.getItemId();
                Intent intent = new Intent();

                switch (itemId) {
                    case  R.id.navigation_review_order :
                        DeliveryPlaceActivity.super.onBackPressed();
                        result = true;
                        break;

                    case R.id.navigation_payment_type :
                        if(checkSelection()) {
                            intent.setClass(mContext,PaymentTypeActivity.class);
                            startActivity(intent);
                            result = true;
                        }
                        break;
                }
                return result;
            }
        });


        // Disable/enable spinner based on radio button clicked

        deliveriesRadioGroup.clearCheck();
        toggleRadioButtonDetails(firstChoiceLayout);
        toggleRadioButtonDetails(secondChoideLayout);


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

    public boolean checkSelection () {
        boolean result = false;

        if(deliveriesRadioGroup.getCheckedRadioButtonId() != -1){
            result = true;
        } else {
            Toast.makeText(mContext, "Devi effettuare una scelta", Toast.LENGTH_LONG).show();
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
        bottomNavigationMenu =  (BottomNavigationView) findViewById(R.id.delivery_bottom_navigation);

        tablesSpinner = (Spinner) findViewById(R.id.tables_drop_down);
        countersSpinner = (Spinner) findViewById(R.id.counters_drop_down);

        deliveriesRadioGroup = (RadioGroup) findViewById(R.id.deliveries_radio_group);

        firstChoiceRadioButton = (RadioButton) findViewById(R.id.first_delivery_choice);
        secondChoiceRadioButton = (RadioButton) findViewById(R.id.second_delivery_choice);

        firstChoiceLayout = (LinearLayout) findViewById(R.id.first_delivery_choice_layout);
        secondChoideLayout = (LinearLayout) findViewById(R.id.second_delivery_choice_layout);
    }

}
