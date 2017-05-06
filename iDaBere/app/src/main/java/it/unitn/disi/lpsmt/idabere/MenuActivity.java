package it.unitn.disi.lpsmt.idabere;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

public class MenuActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        initViewComps();

    }

    // Instantiate layout elements
    private void initViewComps () {
        mRecyclerView = (RecyclerView) findViewById(R.id.list_item_recycler_view);

    }

}
