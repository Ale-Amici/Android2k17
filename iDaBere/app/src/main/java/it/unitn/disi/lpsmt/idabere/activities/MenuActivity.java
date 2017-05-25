package it.unitn.disi.lpsmt.idabere.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageButton;

import it.unitn.disi.lpsmt.idabere.R;
import it.unitn.disi.lpsmt.idabere.adapters.MenuCategoryExpandableListAdapter;
import it.unitn.disi.lpsmt.idabere.models.Bar;
import it.unitn.disi.lpsmt.idabere.models.BarMenu;
import it.unitn.disi.lpsmt.idabere.models.BarMenuItem;
import it.unitn.disi.lpsmt.idabere.session.AppSession;

public class MenuActivity extends AppCompatActivity implements
        SearchView.OnQueryTextListener{

    private ExpandableListView categoriesExpandableListView;
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
        BarMenu tempMenu = new BarMenu();
        BarMenuItem tempItem1 = new BarMenuItem();
        tempItem1.setName("Vino Rosso");
        tempItem1.setCategory("Vini");
        BarMenuItem tempItem2 = new BarMenuItem();
        tempItem2.setName("Vino bianco");
        tempItem2.setCategory("Vini");
        BarMenuItem tempItem3 = new BarMenuItem();
        tempItem3.setName("Birra Rossa");
        tempItem3.setCategory("Birre");

        tempMenu.getBarMenuItemList().add(tempItem1);
        tempMenu.getBarMenuItemList().add(tempItem2);
        tempMenu.getBarMenuItemList().add(tempItem3);

       // menuCategoryExpandableListAdapter = new MenuCategoryExpandableListAdapter(this, AppSession.getInstance().getmBar() );
        // setting list adapter
        categoriesExpandableListView.setAdapter(
                //new MenuCategoryExpandableListAdapter(mContext, AppSession.getInstance().getmBar().getBarMenu())
                new MenuCategoryExpandableListAdapter(mContext, tempMenu)
        );

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
        categoriesExpandableListView = (ExpandableListView) findViewById(R.id.categories_expandable_list);

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

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search_bar_icon).getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setSubmitButtonEnabled(false);
        searchView.setOnQueryTextListener(this);

        return super.onCreateOptionsMenu(menu);
    }

    private void addNewChoice (View v) {

    }

    private void openItemInfo (View v) {
        Intent itemInfoIntend = new Intent(mContext, ItemInfoActivity.class);
        startActivity(itemInfoIntend);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        ((MenuCategoryExpandableListAdapter)categoriesExpandableListView.getExpandableListAdapter())
                .getFilter().filter(newText);
        return true;
    }


    private class MenuLoader extends AsyncTask<Bar,Void,Menu> {

        @Override
        protected Menu doInBackground(Bar... params) {

            ListBarActivity.factoryDAO.newBarsDAO().getBarById(params[0]);

            return null;
        }
    }
}