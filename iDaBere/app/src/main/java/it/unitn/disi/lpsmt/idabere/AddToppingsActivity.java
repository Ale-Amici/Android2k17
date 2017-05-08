package it.unitn.disi.lpsmt.idabere;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class AddToppingsActivity extends AppCompatActivity {


//    TODO Creare un array adapter customizzato di prova per visualizzare la lista delle sizes e dei toppings

    private ListView sizesListView;
    private ListView toppingsListView;


    // Fake data variables
    private final String UNIT_MEASURE = "cl";
    private ArrayList<String> SIZES_LIST;
    private ArrayList<String> TOPPINGS_LIST;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_toppings);
        initComps();
        initData();


        

    }

    private void initComps () {
        sizesListView = (ListView) findViewById(R.id.sizes_list_view);
        toppingsListView = (ListView) findViewById(R.id.toppings_list_view);
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
