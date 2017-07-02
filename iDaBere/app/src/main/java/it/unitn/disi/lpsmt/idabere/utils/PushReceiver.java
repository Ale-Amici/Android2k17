package it.unitn.disi.lpsmt.idabere.utils;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.content.Context;
import android.media.RingtoneManager;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import it.unitn.disi.lpsmt.idabere.R;
import it.unitn.disi.lpsmt.idabere.activities.LoginActivity;
import it.unitn.disi.lpsmt.idabere.activities.OrderStatusActivity;
import it.unitn.disi.lpsmt.idabere.session.AppSession;

/**
 * Created by giovanni on 19/06/2017.
 *
 * Any payload data that you send with your push notifications is made available to your app via the Intent extras of your PushReceiver class.
 *
 * If you were to send a push notification with the following data:
 *
 * {"id": 1, "success": true, "message": "Hello World"}
 * Then you'd be able to retrieve each value from within your PushReceiver.java file like so:
 *
 * int id = intent.getIntExtra("id", 0);
 * String message = intent.getStringExtra("message");
 * boolean success = intent.getBooleanExtra("success", false);
 * Note: Unlike GCM / FCM, we do not stringify your payload data, except if you supply JSON objects or arrays. This means that if you send {"id": 3}, you'd retrieve that value in the receiver using intent.getIntExtra("id", 0).
 *
 *
 */
public class PushReceiver extends BroadcastReceiver {

    public static final int ORDER_NOTIFICATION_REQUEST_CODE = 200;

    public static final HashMap<String,ArrayList<String>> ORDER_STATUSES = new HashMap<String, ArrayList<String>>() {{
        put("PAYMENT_IN_PROGRESS",new ArrayList<String>(){{
            add("In attesa di pagamento");
            add("Recati alla cassa per effettuare il pagamento");
        }});
        put("IN_QUEUE",new ArrayList<String>(){{
            add("In coda");
            add("Attendi che il tuo ordine venga preso in carico da un cameriere");
        }});
        put("IN_PREPARATION",new ArrayList<String>(){{
            add("In preparazione");
            add("Il tuo ordine è in preparazione");
        }});
        put("READY",new ArrayList<String>(){{
            add("Pronto per la consegna");
            add("Recati al bancone per ritirare il tuo ordine e mostra questo QR");
            add("Attendi che il tuo ordine venga servito al tuo tavolo e mostra questo QR");
        }});
        put("COMPLETED",new ArrayList<String>(){{
            add("Completato");
        }});

    }};

    @Override
    public void onReceive(Context context, Intent intent) {
        String notificationTitle = "Aggiornamento dello stato dell'ordine";
        String notificationText = "Test notification";

        // Attempt to extract the "message" property from the payload: {"message":"Hello World!"}
        if (intent.getStringExtra("message") != null) {
            //Log.d("MESSAGE", intent.getStringExtra("message"));
            notificationText = prettifyMessage(intent.getStringExtra("message"));
        }

        AppSession.getInstance().getmCustomer().getOrder().setStatus(intent.getStringExtra("message"));

        // Prepare a notification with vibration, sound and lights
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_notif_icon_black)
                .setContentTitle(notificationTitle)
                .setContentText(notificationText)
                .setAutoCancel(true)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(notificationText))
                .setCategory(Notification.CATEGORY_PROGRESS)
                .setVibrate(new long[]{0, 400, 250, 400})
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(PendingIntent.getActivity(context, ORDER_NOTIFICATION_REQUEST_CODE, new Intent(context, LoginActivity.class), PendingIntent.FLAG_UPDATE_CURRENT));

        // Get an instance of the NotificationManager service
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

        // Build the notification and display it
        notificationManager.notify(1, builder.build());

        Intent updateStatusIntent = new Intent("UPDATE_UI");
        if (OrderStatusActivity.isAppInFg){
            LocalBroadcastManager.getInstance(context).sendBroadcast(updateStatusIntent);
        }
    }

    private String prettifyMessage(String notificationText) {
        String message = "";
        ArrayList<String> status = ORDER_STATUSES.get(notificationText);
        if (! status.isEmpty()){
            message += "Lo stato del tuo ordine è: " + status.get(0);
        }
        return message;
    }

}
