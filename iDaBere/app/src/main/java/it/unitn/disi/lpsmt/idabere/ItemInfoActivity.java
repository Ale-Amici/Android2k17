package it.unitn.disi.lpsmt.idabere;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ItemInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_info);

        // Set back arrow button
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
