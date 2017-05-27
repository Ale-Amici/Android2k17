package it.unitn.disi.lpsmt.idabere.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ListView;
import android.widget.RadioButton;

import java.util.ArrayList;

import it.unitn.disi.lpsmt.idabere.R;
import it.unitn.disi.lpsmt.idabere.adapters.AdditionListArrayListAdapter;

public class AddChoiceActivity extends AppCompatActivity {


    private ListView sizeListView;
    private ListView additionListView;

    private AdditionListArrayListAdapter additionListArrayListAdapter;


    // Fake data variables
    private final String UNIT_MEASURE = "cl";
    private ArrayList<String> SIZE_LIST;
    private ArrayList<String> ADDITION_LIST;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_choice);
        initData();

        displaySizesList();

        additionListView = (ListView) findViewById(R.id.additions_list_view);

        additionListArrayListAdapter = new AdditionListArrayListAdapter(this, R.layout.addition_choice, ADDITION_LIST);
        additionListView.setAdapter(additionListArrayListAdapter);

    }


    private void displaySizesList () {
        ViewGroup sizeRadioGroup = (ViewGroup) findViewById(R.id.sizeRadioGroup);  // This is the id of the RadioGroup we defined
        for (int i = 0; i < SIZE_LIST.size(); i++) {
            RadioButton button = new RadioButton(this);
            button.setId(i);
            button.setText(SIZE_LIST.get(i).toString() +" "+ getString(R.string.drinks_measure_units));
            sizeRadioGroup.addView(button);
        }

    }

    private void initData () {

        SIZE_LIST = new ArrayList();
        SIZE_LIST.add("25");
        SIZE_LIST.add("50");
        SIZE_LIST.add("75");

        ADDITION_LIST = new ArrayList();
        ADDITION_LIST.add("Ghiaccio");
        ADDITION_LIST.add("Limone");

    }
}
