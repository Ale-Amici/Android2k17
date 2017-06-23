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
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import it.unitn.disi.lpsmt.idabere.R;
import it.unitn.disi.lpsmt.idabere.adapters.MenuCategoryExpandableListAdapter;
import it.unitn.disi.lpsmt.idabere.models.Addition;
import it.unitn.disi.lpsmt.idabere.models.Bar;
import it.unitn.disi.lpsmt.idabere.models.BarMenu;
import it.unitn.disi.lpsmt.idabere.models.BarMenuItem;
import it.unitn.disi.lpsmt.idabere.models.Customer;
import it.unitn.disi.lpsmt.idabere.models.Order;
import it.unitn.disi.lpsmt.idabere.models.OrderItem;
import it.unitn.disi.lpsmt.idabere.models.Size;
import it.unitn.disi.lpsmt.idabere.session.AppSession;

public class MenuActivity extends AppCompatActivity implements
        SearchView.OnQueryTextListener{

    // The index of total price menu item associated with bottom navigation menu
    final int TOTAL_PRICE_MENU_ITEM_INDEX = 1;
    private ExpandableListView categoriesExpandableListView;
    private BottomNavigationView bottomNavigationMenu;

    private View progressBar;
    private Button newChoiceButton;
    private ImageButton itemInfoButton;

    private TextView totalPriceInfo;

    private Context mContext;

    private BarMenu barMenu;

    private MenuCategoryExpandableListAdapter menuAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        initViewComps();
        mContext = this;

       // menuCategoryExpandableListAdapter = new MenuCategoryExpandableListAdapter(this, AppSession.getInstance().getmBar() );
        // setting list adapter
        new MenuLoader().execute();

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

        //new MenuLoader().execute(AppSession.getInstance().getmBar());

    }

    @Override
    protected void onResume() {
        if (AppSession.getInstance().getmCustomer() != null && AppSession.getInstance().getmCustomer().getOrder() != null){
            totalPriceInfo.setText(Double.toString(AppSession.getInstance().getmCustomer().getOrder().getTotalPrice()));
        }
        if(menuAdapter != null){
            menuAdapter.notifyDataSetChanged();
        }
        super.onResume();
    }

    // Instantiate layout elements
    private void initViewComps () {
        // get the listview
        categoriesExpandableListView = (ExpandableListView) findViewById(R.id.categories_expandable_list);

        // get the bottom navigation menu
        bottomNavigationMenu =  (BottomNavigationView) findViewById(R.id.menu_bottom_navigation);

        progressBar = findViewById(R.id.loading_indicator);
        newChoiceButton = (Button) findViewById(R.id.add_choice_button);
        itemInfoButton = (ImageButton) findViewById(R.id.item_info_button);

        // the total price at the bottom menu
        totalPriceInfo = (TextView) findViewById(R.id.menu_total_order_price);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        menuAdapter = (MenuCategoryExpandableListAdapter) categoriesExpandableListView.getExpandableListAdapter();
        AddChoiceActivity.checkNewChoiceResult(requestCode, resultCode, data, mContext, menuAdapter);

        // TODO 4 FAI SI CHE OGNI ALTRA CATEGORIA SI CHIUDA QUANDO NE APRI UN'ALTRA

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


    private class MenuLoader extends AsyncTask<Bar,Void,BarMenu> {

        @Override
        protected BarMenu doInBackground(Bar... params) {
            AppSession.getInstance().setmBar(ListBarActivity.factoryDAO.newBarsDAO().getBarById(AppSession.getInstance().getmBar()));
            Bar currentBar = AppSession.getInstance().getmBar();
            if (currentBar != null) {
                barMenu = AppSession.getInstance().getmBar().getBarMenu();
                Log.d("BAR_MENU", barMenu.toString());
                Log.d("BAR", ListBarActivity.factoryDAO.newBarsDAO().getBarById(AppSession.getInstance().getmBar()).toString());
            }


            return barMenu;
        }

        @Override
        protected void onPostExecute(BarMenu barMenu) {
            if (barMenu != null){
                menuAdapter = new MenuCategoryExpandableListAdapter(mContext, barMenu, totalPriceInfo, categoriesExpandableListView);
                categoriesExpandableListView.setAdapter(menuAdapter);
            } else {
                Toast.makeText(mContext, "Servizio al momento non disponibile.", Toast.LENGTH_SHORT).show();
            }


            super.onPostExecute(barMenu);
        }
    }

}