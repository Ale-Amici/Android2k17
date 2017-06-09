package it.unitn.disi.lpsmt.idabere.activities;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;

import it.unitn.disi.lpsmt.idabere.R;
import it.unitn.disi.lpsmt.idabere.adapters.AdditionListArrayListAdapter;
import it.unitn.disi.lpsmt.idabere.models.BarMenu;
import it.unitn.disi.lpsmt.idabere.models.BarMenuItem;
import it.unitn.disi.lpsmt.idabere.session.AppSession;

public class AddChoiceActivity extends AppCompatActivity {

    private Button addChoiceButton;
    private Button cancelChoiceButton;


    private ListView sizeListView;
    private ListView additionListView;
    private RadioGroup sizeRadioGroup;


    private AdditionListArrayListAdapter additionListArrayListAdapter;


    BarMenuItem mBarMenuItem;
    // Fake data variables
    private final String UNIT_MEASURE = "cl";
    private ArrayList<String> SIZE_LIST;
    private ArrayList<String> ADDITION_LIST;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_choice);
        sizeRadioGroup = (RadioGroup) findViewById(R.id.sizeRadioGroup);
        additionListView = (ListView) findViewById(R.id.additions_list_view);

        /* ***GET THE BAR_MENU_ITEM SELECTED*** */
        int itemId = this.getIntent().getIntExtra("barMenuItemId",-1);
        mBarMenuItem = AppSession.getInstance().getmBar().getBarMenu().getBarMenuItemFromId(itemId);
        if(mBarMenuItem == null){
            System.out.println("\n\nERRORE, NESSUN BAR_MENU_ITEM CON id=" + itemId +"\n\n");
            setResult(RESULT_CANCELED);
            finish();
        }
        /* ************************************* */

        /* *** BUTTONS CONFIGURATION *** */
        addChoiceButton = (Button) findViewById(R.id.add_choice_button);
        cancelChoiceButton = (Button) findViewById(R.id.cancel_chioce_button);

        addChoiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //INSERISCO I RISULTATI E LI RESTITUISCO AL MENU
                Intent newChoice = new Intent();
                ArrayList<Integer> selectedAdditionsIds = new ArrayList<Integer>();
                if(mBarMenuItem.getAdditions() != null) {
                    selectedAdditionsIds = new ArrayList<Integer> (((AdditionListArrayListAdapter)additionListView.getAdapter()).getSelectedAdditionsIds());
                }
                newChoice.putExtra("chosenAdditionsIds", selectedAdditionsIds);//ArrayList of Ids of Addition
                newChoice.putExtra("chosenSizeId", mBarMenuItem.getSizes().get(sizeRadioGroup.getCheckedRadioButtonId()).getId()); //Id of the size
                newChoice.putExtra("chosenBarMenuItemId", mBarMenuItem.getId());

                setResult(RESULT_OK, newChoice);
                finish();
            }
        });

        cancelChoiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
        /* ***************************** */

        initData();

        displaySizesList();


        if(mBarMenuItem.getAdditions() != null) {
            additionListArrayListAdapter = new AdditionListArrayListAdapter(this, R.layout.addition_choice, mBarMenuItem.getAdditions());
            additionListView.setAdapter(additionListArrayListAdapter);
        }
        else{
            TextView additionsLabel = (TextView)findViewById(R.id.additions_title_label);
            additionsLabel.setText("");
        }

    }


    private void displaySizesList () {


        for (int i = 0; i < mBarMenuItem.getSizes().size(); i++) {
            RadioButton button = new RadioButton(this);
            button.setId(i);
            //button.setTag(i);
            button.setText(mBarMenuItem.getSizes().get(i).getName());
            sizeRadioGroup.addView(button);
        }
        sizeRadioGroup.check(0);

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
