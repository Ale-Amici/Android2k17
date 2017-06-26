package it.unitn.disi.lpsmt.idabere.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import it.unitn.disi.lpsmt.idabere.R;
import it.unitn.disi.lpsmt.idabere.models.Order;
import it.unitn.disi.lpsmt.idabere.session.AppSession;
import me.pushy.sdk.Pushy;

public class OrderStatusActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);

    }

    @Override
    public void onBackPressed() {
        Order currentOrder = AppSession.getInstance().getmCustomer().getOrder();
        if (currentOrder == null || currentOrder.getId() == -1){
            super.onBackPressed();
        } else {
            View container = findViewById(R.id.main_activity_container);
            if (container != null) {
                Snackbar.make(container, getResources().getString(R.string.wait_order_message), Snackbar.LENGTH_LONG).show();
            }
        }
    }
}
