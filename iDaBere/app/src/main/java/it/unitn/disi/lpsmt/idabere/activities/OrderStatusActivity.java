package it.unitn.disi.lpsmt.idabere.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import it.unitn.disi.lpsmt.idabere.R;
import me.pushy.sdk.Pushy;

public class OrderStatusActivity extends AppCompatActivity {

    // Url per contattare la macchina in locale tramite il servizio ngrook
    // TODO Una volta aperto il servizio sostituire <id>
    final String ngrokUrlId = "<id>";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);

        Pushy.listen(this);
        // Check whether the user has granted us the READ/WRITE_EXTERNAL_STORAGE permissions
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // Request both READ_EXTERNAL_STORAGE and WRITE_EXTERNAL_STORAGE so that the
            // Pushy SDK will be able to persist the device token in the external storage
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }

        new RegisterForPushNotificationsAsync().execute();


    }

    private class RegisterForPushNotificationsAsync extends AsyncTask<Void, Void, Exception> {
        protected Exception doInBackground(Void... params) {
            try {
                // Assign a unique token to this device
                String deviceToken = Pushy.register(getApplicationContext());

                // Log it for debugging purposes
                Log.d("MyApp", "Pushy device token: " + deviceToken);

                // Send the token to your backend server via an HTTP GET request
                URL url = new URL("http://"+ngrokUrlId+".ngrok.io/notifications/register/" + deviceToken);
                Log.d("URL", url.toString());
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    Log.d("RESPONSE", in.toString());
                } finally {
                    urlConnection.disconnect();
                }
            }
            catch (Exception exc) {
                // Return exc to onPostExecute
                return exc;
            }

            // Success
            return null;
        }

        @Override
        protected void onPostExecute(Exception exc) {
            // Failed?
            if (exc != null) {
                // Show error as toast message
                Toast.makeText(getApplicationContext(), exc.toString(), Toast.LENGTH_LONG).show();
                return;
            }

            // Succeeded, do something to alert the user
        }
    }


}
