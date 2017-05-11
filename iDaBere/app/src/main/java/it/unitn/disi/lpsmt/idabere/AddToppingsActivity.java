package it.unitn.disi.lpsmt.idabere;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

import it.unitn.disi.lpsmt.idabere.adapters.ToppingsListArrayListAdapter;

public class AddToppingsActivity extends AppCompatActivity {


//    TODO Creare un array adapter customizzato di prova per visualizzare la lista delle sizes e dei toppings

    private ListView sizesListView;
    private ListView toppingsListView;

    private ToppingsListArrayListAdapter toppingsListArrayListAdapter;


    // Fake data variables
    private final String UNIT_MEASURE = "cl";
    private ArrayList<String> SIZES_LIST;
    private ArrayList<String> TOPPINGS_LIST;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_toppings);
        initData();

        displaySizesList();

        toppingsListView = (ListView) findViewById(R.id.toppings_list_view);

        toppingsListArrayListAdapter = new ToppingsListArrayListAdapter(this, R.layout.topping_choice, TOPPINGS_LIST);
        toppingsListView.setAdapter(toppingsListArrayListAdapter);

    }


    private void displaySizesList () {
        ViewGroup sizeRadioGroup = (ViewGroup) findViewById(R.id.sizeRadioGroup);  // This is the id of the RadioGroup we defined
        for (int i = 0; i < SIZES_LIST.size(); i++) {
            RadioButton button = new RadioButton(this);
            button.setId(i);
            button.setText(SIZES_LIST.get(i).toString() +" "+ getString(R.string.drinks_measure_units));
            sizeRadioGroup.addView(button);
        }

    }

    private void initData () {

        SIZES_LIST = new ArrayList();
        SIZES_LIST.add("25");
        SIZES_LIST.add("50");
        SIZES_LIST.add("75");

        TOPPINGS_LIST = new ArrayList();
        TOPPINGS_LIST.add("Ghiaccio");
        TOPPINGS_LIST.add("Limone");

    }
}
