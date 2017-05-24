package it.unitn.disi.lpsmt.idabere.activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import it.unitn.disi.lpsmt.idabere.R;
import it.unitn.disi.lpsmt.idabere.adapters.MenuCategoryExpandableListAdapter;
import it.unitn.disi.lpsmt.idabere.models.Bar;
import it.unitn.disi.lpsmt.idabere.session.AppSession;

public class MenuActivity extends AppCompatActivity {

    private MenuCategoryExpandableListAdapter menuCategoryExpandableListAdapter;
    private ExpandableListView categoriesExpandableList;
    private BottomNavigationView bottomNavigationMenu;

    private View progressBar;
    private Button newChoiceButton;
    private ImageButton itemInfoButton;

    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        initViewComps();
        mContext = this;

       // menuCategoryExpandableListAdapter = new MenuCategoryExpandableListAdapter(this, AppSession.getInstance().getmBar() );
        // setting list adapter
        categoriesExpandableList.setAdapter(menuCategoryExpandableListAdapter);

        bottomNavigationMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                boolean result = false;
                int itemId = item.getItemId();
                switch (itemId) {
                    case R.id.navigation_review_order :
                        Intent intent = new Intent();
                        intent.setClass(mContext,ReviewOrderActivity.class);
                        startActivity(intent);
                        result = true;
                        break;
                }
                return result;
            }
        });

        new MenuLoader().execute(AppSession.getInstance().getmBar());

    }

    // Instantiate layout elements
    private void initViewComps () {
        // get the listview
        categoriesExpandableList = (ExpandableListView) findViewById(R.id.categories_expandable_list);

        // get the bottom navigation menu
        bottomNavigationMenu =  (BottomNavigationView) findViewById(R.id.menu_bottom_navigation);

        progressBar = findViewById(R.id.loading_indicator);
        newChoiceButton = (Button) findViewById(R.id.add_topping_button);
        itemInfoButton = (ImageButton) findViewById(R.id.item_info_button);

        //set activity title based to bar instance
        setTitle(AppSession.getInstance().getmBar().getName());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.item_list_menu,menu);
        
        return super.onCreateOptionsMenu(menu);
    }

    private void addNewChoice (View v) {

    }

    private void openItemInfo (View v) {
        Intent itemInfoIntend = new Intent(mContext, ItemInfoActivity.class);
        startActivity(itemInfoIntend);
    }


    private class MenuLoader extends AsyncTask<Bar,Void,Menu> {

        @Override
        protected Menu doInBackground(Bar... params) {

            ListBarActivity.factoryDAO.newBarsDAO().getBarById(params[0]);

            return null;
        }
    }


}
