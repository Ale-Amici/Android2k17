package it.unitn.disi.lpsmt.idabere.activities;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.Random;

import it.unitn.disi.lpsmt.idabere.R;
import it.unitn.disi.lpsmt.idabere.models.BarMenu;
import it.unitn.disi.lpsmt.idabere.models.BarMenuItem;
import it.unitn.disi.lpsmt.idabere.models.Customer;
import it.unitn.disi.lpsmt.idabere.session.AppSession;

public class ItemInfoActivity extends AppCompatActivity {

    private RatingBar mItemRatingBar;
    private TextView mItemDescription;
    private ImageView mItemImage;

    private MenuItem preferredButton;

    private BarMenuItem menuItem;
    private int menuItemId;

    final String ITEM_CLICKED_ID_KEY = "ITEM_ID";

    private float randomRating = new Random().nextFloat();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_info);

        
        menuItemId = getIntent().getIntExtra(ITEM_CLICKED_ID_KEY,0);
        menuItem = AppSession.getInstance().getmBar().getBarMenu().getBarMenuItemById(menuItemId);
        initViewComps();

        // TODO set del valore del rating
        mItemRatingBar.setRating(5*randomRating);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.item_info_menu, menu);
        preferredButton = menu.findItem(R.id.add_preferred_button);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            togglePreferredButtonState();
        }
        if (AppSession.getInstance().getmCustomer() == null || AppSession.getInstance().getmCustomer().getId() == -1){
            preferredButton.setVisible(false);
        } else {
            preferredButton.setVisible(true);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean result = false;

        switch (item.getItemId()){
            case R.id.add_preferred_button :
                addtoPreferreds();
                result = true;
                break;
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void togglePreferredButtonState() {
        AppSession currentSession = AppSession.getInstance();
        BarMenu currentBarMenu = currentSession.getmBar().getBarMenu();
        Customer currentCustomer = currentSession.getmCustomer();
        BarMenuItem currentBarMenuItem = currentBarMenu.getBarMenuItemById(menuItemId);
        Log.d("INDEX", Integer.toString(currentCustomer.getPreferredItems().indexOf(currentBarMenuItem)));
        if (currentCustomer.getPreferredItems().size() == 0 || currentCustomer.getPreferredItems().indexOf(currentBarMenuItem) == -1 ){
            preferredButton.setIcon(getDrawable(R.drawable.ic_bookmark_border_black_24dp));
        } else {
            preferredButton.setIcon(getDrawable(R.drawable.ic_bookmark_black_24dp));
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void addtoPreferreds () {
        AppSession currentSession = AppSession.getInstance();
        BarMenu currentBarMenu = currentSession.getmBar().getBarMenu();
        Customer currentCustomer = currentSession.getmCustomer();
        BarMenuItem currentBarMenuItem = currentBarMenu.getBarMenuItemById(menuItemId);
        if (currentCustomer.getPreferredItems().size() == 0 || currentCustomer.getPreferredItems().indexOf(currentBarMenuItem) == -1 ){
            currentCustomer.getPreferredItems().add(currentBarMenuItem);
            togglePreferredButtonState();
        } else {
            currentCustomer.getPreferredItems().remove(currentBarMenuItem);
            togglePreferredButtonState();
        }
        Log.d("PREFERRED ITEMS", currentCustomer.getPreferredItems().toString());

    }


    private void initViewComps() {
        mItemDescription = (TextView) findViewById(R.id.item_info_content);
        mItemImage = (ImageView) findViewById(R.id.item_image_view);
        mItemRatingBar = (RatingBar) findViewById(R.id.item_rating_bar);

        setTitle(menuItem.getName());
        mItemDescription.setText(menuItem.getDescription());

        // TODO Inserire il valore del rating

    }
}
