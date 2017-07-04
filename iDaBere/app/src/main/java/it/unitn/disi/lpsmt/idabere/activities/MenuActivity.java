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
import android.util.Pair;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

import it.unitn.disi.lpsmt.idabere.DAOIntefaces.CustomersDAO;
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
import it.unitn.disi.lpsmt.idabere.utils.AppStatus;

public class MenuActivity extends AppCompatActivity implements
        SearchView.OnQueryTextListener {

    // The index of total price menu item associated with bottom navigation menu
    final int TOTAL_PRICE_MENU_ITEM_INDEX = 1;

    private View loadingIndicator;
    private ExpandableListView categoriesExpandableListView;
    private BottomNavigationView bottomNavigationMenu;

    private TextView totalPriceInfo;
    private TextView totalItemsTV;


    private Context mContext;

    private BarMenu barMenu;

    private MenuCategoryExpandableListAdapter menuAdapter;

    private MenuItem searchMenuItem;
    private MenuItem randomDrinkMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        initViewComps();
        mContext = this;

        bottomNavigationMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                boolean result = false;
                int itemId = item.getItemId();
                switch (itemId) {
                    case R.id.navigation_review_order:
                        Intent intent = new Intent();
                        intent.setClass(mContext, ReviewOrderActivity.class);
                        startActivity(intent);
                        result = true;
                        break;
                    case R.id.navigation_list_bar :
                        MenuActivity.super.onBackPressed();
                }
                return result;
            }
        });

        categoriesExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                int index = parent.getFlatListPosition(ExpandableListView
                        .getPackedPositionForChild(groupPosition, childPosition));
                parent.setItemChecked(index, true);

                return false;
            }
        });

        new MenuLoader().execute();

    }

    @Override
    protected void onResume() {
        Order currentOrder = AppSession.getInstance().getmCustomer().getOrder();
        if (currentOrder != null) {
            double totalPrice = AppSession.getInstance().getmCustomer().getOrder().getTotalPrice();
            int totalItems = AppSession.getInstance().getmCustomer().getOrder().getTotalQuantity();
            totalPriceInfo.setText(new DecimalFormat("##0.00").format(totalPrice));
            totalItemsTV.setText(Integer.toString(totalItems));
        }
        if (menuAdapter != null) {
            menuAdapter.refreshMenuAdapter();
        }
        super.onResume();
    }

    // Instantiate layout elements
    private void initViewComps() {

        // get the listview
        categoriesExpandableListView = (ExpandableListView) findViewById(R.id.categories_expandable_list);
        loadingIndicator = findViewById(R.id.loading_indicator);

        // get the bottom navigation menu
        bottomNavigationMenu = (BottomNavigationView) findViewById(R.id.menu_bottom_navigation);

        // the total price at the bottom menu
        totalPriceInfo = (TextView) findViewById(R.id.total_order_price);
        totalItemsTV = (TextView) findViewById(R.id.total_order_items);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.item_list_menu, menu);

        //I MENU ITEM SONO PRESI E DISABILITATI
        searchMenuItem = menu.findItem(R.id.action_search_bar_icon);
        randomDrinkMenuItem = menu.findItem(R.id.random_drink_menu_button);
        searchMenuItem.setEnabled(false);
        randomDrinkMenuItem.setEnabled(false);

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
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean result;
        switch (item.getItemId()){
            case R.id.random_drink_menu_button :
                selectRandomDrink();
                result = true;
                break;
            default:
                result = super.onOptionsItemSelected(item);
        }
        return result;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        menuAdapter = (MenuCategoryExpandableListAdapter) categoriesExpandableListView.getExpandableListAdapter();
        AddChoiceActivity.checkNewChoiceResult(requestCode, resultCode, data, mContext, menuAdapter);
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        ((MenuCategoryExpandableListAdapter) categoriesExpandableListView.getExpandableListAdapter())
                .getFilter().filter(newText);
        return true;
    }


    private class MenuLoader extends AsyncTask<Bar, Void, BarMenu> {

        @Override
        protected void onPreExecute() {
            if(searchMenuItem != null){
                searchMenuItem.setEnabled(false);
            }
            if(randomDrinkMenuItem != null){
                randomDrinkMenuItem.setEnabled(false);
            }

            toggleLoading(true);
            super.onPreExecute();
        }

        @Override
        protected BarMenu doInBackground(Bar... params) {
            Bar newBar = WelcomeActivity.factoryDAO.newBarsDAO().getBarById(AppSession.getInstance().getmBar());

            if (newBar != null){
                AppSession.getInstance().setmBar(newBar);

                if (getIntent().getBooleanExtra("BAR_CHANGED", false)) {
                    Order currentOrder = AppSession.getInstance().getmCustomer().getOrder();
                    if (currentOrder != null) {
                        currentOrder.getOrderItems().clear();
                    }
                }
                AppSession.getInstance().getmCustomer().getOrder().setChosenBarId(newBar.getId());

                barMenu = AppSession.getInstance().getmBar().getBarMenu();
                Log.d("BAR_MENU", barMenu.toString());
                Log.d("BAR", WelcomeActivity.factoryDAO.newBarsDAO().getBarById(AppSession.getInstance().getmBar()).toString());
            }

            return barMenu;
        }

        @Override
        protected void onPostExecute(BarMenu retrievedBarMenu) {

            if (retrievedBarMenu != null) {
                searchMenuItem.setEnabled(true);
                randomDrinkMenuItem.setEnabled(true);
                retrievedBarMenu.applyDiscounts();
                menuAdapter = new MenuCategoryExpandableListAdapter(mContext, retrievedBarMenu, totalPriceInfo, totalItemsTV, categoriesExpandableListView);
                categoriesExpandableListView.setAdapter(menuAdapter);
            } else {
                Toast.makeText(mContext, "Nessun Bar Trovato", Toast.LENGTH_SHORT).show();
                MenuActivity.super.onBackPressed();
            }

            toggleLoading(false);

            //set activity title based to bar instance
            setTitle(AppSession.getInstance().getmBar().getName());

            super.onPostExecute(retrievedBarMenu);
        }
    }

    private void toggleLoading(boolean isVisible) {
        if (isVisible) {
            loadingIndicator.setVisibility(View.VISIBLE);
            categoriesExpandableListView.setVisibility(View.GONE);
        } else {
            loadingIndicator.setVisibility(View.GONE);
            categoriesExpandableListView.setVisibility(View.VISIBLE);
        }

    }

    private void selectRandomDrink() {
        if(menuAdapter.getGroupCount() != 0) {
            int groupPosition = new Random().nextInt(menuAdapter.getGroupCount());
            int childPosition = new Random().nextInt(menuAdapter.getChildrenCount(groupPosition));
            categoriesExpandableListView.setSelectedChild(groupPosition, childPosition, true);
            menuAdapter.showItemWithPosition(groupPosition, childPosition);
        }

    }

}