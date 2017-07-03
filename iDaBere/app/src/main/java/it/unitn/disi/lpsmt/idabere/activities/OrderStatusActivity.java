package it.unitn.disi.lpsmt.idabere.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import net.glxn.qrgen.android.QRCode;

import java.util.ArrayList;

import it.unitn.disi.lpsmt.idabere.R;
import it.unitn.disi.lpsmt.idabere.models.BarTable;
import it.unitn.disi.lpsmt.idabere.models.Order;
import it.unitn.disi.lpsmt.idabere.session.AppSession;
import it.unitn.disi.lpsmt.idabere.utils.PushReceiver;

public class OrderStatusActivity extends AppCompatActivity {

    private BroadcastReceiver broadcastReceiver;

    private Context mContext;

    // TODO inserire l'url corretto di disturzione ordine
    private final String DESTROY_ORDER_API_URL = "http://151.80.152.226/orders/complete/";
    private ImageView qrCode;

    private TextView orderId;
    //private TextView orderQueue;
    private TextView orderStatusDescriptionTV;
    private TextView orderStatusGuide;

    public static boolean isAppInFg = false;
    public static boolean isScrInFg = false;
    public static boolean isChangeScrFg = false;

    private Order currentOrder = AppSession.getInstance().getmCustomer().getOrder();

    private ArrayList<String> status;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);

        mContext = this;

        initComps();

        Order currentOrder = AppSession.getInstance().getmCustomer().getOrder();

        // Build destruction QR code
        Bitmap myBitmap = QRCode.from(DESTROY_ORDER_API_URL + currentOrder.getId() + "/" + currentOrder.getDestroyCode()).bitmap();
        qrCode.setImageBitmap(myBitmap);
        orderId.setText(Integer.toString(currentOrder.getId()));
        //orderQueue.setText(Integer.toString(new Random().nextInt(50)));


    }

    @Override
    protected void onStart() {
        if (!isAppInFg) {
            isAppInFg = true;
            isChangeScrFg = false;
        } else {
            isChangeScrFg = true;
        }
        isScrInFg = true;

        setOrderDescriptions();
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (!isScrInFg || !isChangeScrFg) {
            isAppInFg = false;
        }
        isScrInFg = false;
    }

    @Override
    protected void onResume() {

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent)
            {

                setOrderDescriptions();

            }
        };
        try {

            LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,new IntentFilter("UPDATE_UI"));

        } catch (Exception e)
        {
            // TODO: handle exception
            e.printStackTrace();
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }


    @Override
    public void onBackPressed() {
        Order currentOrder = AppSession.getInstance().getmCustomer().getOrder();
        if (currentOrder == null || currentOrder.getId() == -1){
            Intent intent = new Intent();
            intent.setClass(this, ListBarActivity.class);
            startActivity(intent);
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
        //orderQueue = (TextView) findViewById(R.id.oder_status_queue);
        orderStatusDescriptionTV = (TextView) findViewById(R.id.oder_status_description_text);
        orderStatusGuide = (TextView) findViewById(R.id.oder_status_description_guide);
    }

    private void setOrderDescriptions () {
        status = PushReceiver.ORDER_STATUSES.get(currentOrder.getStatus());
        orderStatusDescriptionTV.setText(status.get(0));

        if (status.get(0).equals("READY") && currentOrder.getChosenDeliveryPlace() instanceof BarTable){
            orderStatusGuide.setText(status.get(2));
        } else if (currentOrder.getStatus().equals("COMPLETED")) {
            currentOrder.setId(-1);
            Intent concludeIntent = new Intent();
            concludeIntent.setClass(mContext, RateOrderActivity.class);
            startActivity(concludeIntent);
        } else {
            orderStatusGuide.setText(status.get(1));    
        }
    }

}
