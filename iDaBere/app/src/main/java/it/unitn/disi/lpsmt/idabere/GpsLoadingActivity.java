package it.unitn.disi.lpsmt.idabere;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import it.unitn.disi.lpsmt.idabere.R;

public class GpsLoadingActivity extends AppCompatActivity {

    ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps_loading);

        initViewComps();

    }

    private void initViewComps () {

        mLoadingIndicator = (ProgressBar) findViewById(R.id.progressBar);

    }
}
