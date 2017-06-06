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

    static final private int SELECT_NEW_CHOICE_REQUEST = 1;
    private ExpandableListView categoriesExpandableListView;
    private BottomNavigationView bottomNavigationMenu;

    private View progressBar;
    private Button newChoiceButton;
    private ImageButton itemInfoButton;

    private Context mContext;

    private BarMenu barMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        initViewComps();
        mContext = this;
        /*BarMenu tempMenu = new BarMenu();
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
        tempMenu.getBarMenuItemList().add(tempItem3);*/

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

    // Instantiate layout elements
    private void initViewComps () {
        // get the listview
        categoriesExpandableListView = (ExpandableListView) findViewById(R.id.categories_expandable_list);

        // get the bottom navigation menu
        bottomNavigationMenu =  (BottomNavigationView) findViewById(R.id.menu_bottom_navigation);

        progressBar = findViewById(R.id.loading_indicator);
        newChoiceButton = (Button) findViewById(R.id.add_choice_button);
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

    public void addNewChoice (View v) {
        Intent newChoiceIntent = new Intent();
        newChoiceIntent.putExtra("barMenuItemId", (Integer) v.getTag());
        newChoiceIntent.setClass(this, AddChoiceActivity.class);
        startActivityForResult(newChoiceIntent, SELECT_NEW_CHOICE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == SELECT_NEW_CHOICE_REQUEST){
            if(resultCode == RESULT_OK){
                //TAKE DATA FROM INTENT CHOICE
                OrderItem newOrderItem = createNewOrderItemFromIntent(data);

                //ADD THE ORDER OBJECT TO THE ORDER OF THE CUSTOMER
                if(newOrderItem != null) {
                    addOrderItemToSessionOrder(newOrderItem);
                    int i = 0;
                    for (OrderItem orderItem : AppSession.getInstance().getmCustomer().getOrder().getOrderItems()) {
                        String description = orderItem.getSize().getName();
                        for (Addition a : orderItem.getAdditions()) {
                            description += ", " + a.getName();
                        }
                        Log.d("ORDER Item" + i, description);
                        i++;

                    }

                    // TODO CHANGE THE DATA IN THE ADAPTER TO UPDATE THE GUI
                    MenuCategoryExpandableListAdapter menuAdapter = (MenuCategoryExpandableListAdapter) categoriesExpandableListView.getExpandableListAdapter();
                    menuAdapter.notifyDataSetChanged();
                }
                // TODO 4 FAI SI CHE OGNI ALTRA CATEGORIA SI CHIUDA QUANDO NE APRI UN'ALTRA

            }
        }
    }

    OrderItem createNewOrderItemFromIntent(Intent data){
        System.out.println("chosenAdditionsIds" + data.getIntegerArrayListExtra("chosenAdditionsIds"));
        System.out.println("chosenSizeId" + data.getIntExtra("chosenSizeId",-1));
        ArrayList<Integer> chosenAdditionsIds = data.getIntegerArrayListExtra("chosenAdditionsIds");
        int chosenSizeId = data.getIntExtra("chosenSizeId",-1);
        int chosenMenuItemId = data.getIntExtra("chosenBarMenuItemId", -1);

        if(chosenMenuItemId  != -1 && chosenSizeId != -1){ //gli id sono passati correttamente

            int quantity = 1;
            BarMenuItem chosenBarMenuItem = AppSession.getInstance().getmBar().getBarMenu().getBarMenuItemFromId(chosenMenuItemId);
            Size chosenSize = chosenBarMenuItem.getSizeFromId(chosenSizeId);
            ArrayList<Addition> chosenAdditions = new ArrayList<>();
            for(Integer additionId: chosenAdditionsIds){
                chosenAdditions.add(chosenBarMenuItem.getAdditionFromId(additionId));
            }
            double newSingleItemPrice = chosenSize.getPrice();
            for(Addition a: chosenAdditions){
                newSingleItemPrice += a.getPrice();
            }

            OrderItem newOrderItem = new OrderItem(
                    1,
                    newSingleItemPrice,
                    chosenSize,
                    chosenAdditions,
                    chosenBarMenuItem
            );

            return newOrderItem;
        }
        else{
            Toast.makeText(this,"ERROR: ERRORE NELLA CREAZIONE DELL'ORDINE", Toast.LENGTH_LONG);
        }
        return null;

    }

    void addOrderItemToSessionOrder(OrderItem newOrderItem){
        Customer customer = AppSession.getInstance().getmCustomer();
        Order userOrder = customer.getOrder();
        userOrder.getOrderItems().add(newOrderItem);
    }

    public void openItemInfo (View v) {
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


    private class MenuLoader extends AsyncTask<Bar,Void,BarMenu> {

        @Override
        protected BarMenu doInBackground(Bar... params) {
            AppSession.getInstance().setmBar(ListBarActivity.factoryDAO.newBarsDAO().getBarById(AppSession.getInstance().getmBar()));
            barMenu = AppSession.getInstance().getmBar().getBarMenu();
            Log.d("BAR_MENU", barMenu.toString());

            return barMenu;
        }

        @Override
        protected void onPostExecute(BarMenu barMenu) {
            categoriesExpandableListView.setAdapter(
                    //new MenuCategoryExpandableListAdapter(mContext, AppSession.getInstance().getmBar().getBarMenu())
                    new MenuCategoryExpandableListAdapter(mContext, barMenu)
            );
            super.onPostExecute(barMenu);
        }
    }

}