package it.unitn.disi.lpsmt.idabere.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import net.glxn.qrgen.android.QRCode;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

import it.unitn.disi.lpsmt.idabere.R;
import it.unitn.disi.lpsmt.idabere.models.Order;
import it.unitn.disi.lpsmt.idabere.session.AppSession;
import me.pushy.sdk.Pushy;

public class OrderStatusActivity extends AppCompatActivity {

    // TODO inserire l'url corretto di disturzione ordine
    private final String DESTROY_ORDER_API_URL = "http://151.80.152.226/orders/complete/";
    private ImageView qrCode;
    private TextView orderId;
    private TextView orderQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);

        initComps();

        Order currentOrder = AppSession.getInstance().getmCustomer().getOrder();

        // Build destruction QR code
        Bitmap myBitmap = QRCode.from(DESTROY_ORDER_API_URL + currentOrder.getDestroyCode()).bitmap();
        qrCode.setImageBitmap(myBitmap);
        orderId.setText(Integer.toString(currentOrder.getId()));
        orderQueue.setText(Integer.toString(new Random().nextInt(50)));

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

    private void initComps(){
        qrCode = (ImageView) findViewById(R.id.qr_code_image);
        orderId = (TextView) findViewById(R.id.oder_status_id);
        orderQueue = (TextView) findViewById(R.id.oder_status_queue);
    }

}
