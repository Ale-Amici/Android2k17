package it.unitn.disi.lpsmt.idabere.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import it.unitn.disi.lpsmt.idabere.R;
import it.unitn.disi.lpsmt.idabere.adapters.RatingItemsListAdapter;

public class RateOrderActivity extends AppCompatActivity {

    private Context mContext;

    private RatingItemsListAdapter ratingItemsListAdapter;
    private ListView ratingItemsList;
    private Button finishButton;

    // Fake Data
    ArrayList<String> listDataHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_order);

        mContext = this;

        initView();

        setTitle("");

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToListBar();
            }
        });


    }


    private void goToListBar(){
        Intent finishIntent = new Intent();
        finishIntent.setClass(mContext, ListBarActivity.class);
        finishIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(finishIntent);
    }

    @Override
    public void onBackPressed() {
        goToListBar();
    }

    private void initView () {

        ratingItemsList = (ListView) findViewById(R.id.items_list_view);
        finishButton = (Button) findViewById(R.id.finish_button);
    }

}
