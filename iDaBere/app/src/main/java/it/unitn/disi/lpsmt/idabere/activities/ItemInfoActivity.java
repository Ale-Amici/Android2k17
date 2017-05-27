package it.unitn.disi.lpsmt.idabere.activities;

import android.media.Rating;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import it.unitn.disi.lpsmt.idabere.R;
import it.unitn.disi.lpsmt.idabere.models.BarMenuItem;
import it.unitn.disi.lpsmt.idabere.session.AppSession;

public class ItemInfoActivity extends AppCompatActivity {

    private RatingBar mItemRatingBar;
    private TextView mItemDescription;
    private ImageView mItemImage;

    private BarMenuItem menuItem;
    private int menuItemId;

    final String ITEM_CLICKED_ID_KEY = "ITEM_ID";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_info);

        
        menuItemId = getIntent().getIntExtra(ITEM_CLICKED_ID_KEY,0);
        menuItem = AppSession.getInstance().getmBar().getBarMenu().getBarMenuItemById(menuItemId);
        initViewComps();

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
