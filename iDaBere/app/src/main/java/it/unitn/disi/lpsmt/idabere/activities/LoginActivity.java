package it.unitn.disi.lpsmt.idabere.activities;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import it.unitn.disi.lpsmt.idabere.DAOIntefaces.CustomersDAO;
import it.unitn.disi.lpsmt.idabere.DAOIntefaces.FactoryDAO;
import it.unitn.disi.lpsmt.idabere.DAOInterfacesImpl.CustomersDAOImpl;
import it.unitn.disi.lpsmt.idabere.DAOInterfacesImpl.FactoryDAOImpl;
import it.unitn.disi.lpsmt.idabere.R;
import it.unitn.disi.lpsmt.idabere.models.Customer;
import it.unitn.disi.lpsmt.idabere.models.DeliveryPlace;
import it.unitn.disi.lpsmt.idabere.session.AppSession;
import it.unitn.disi.lpsmt.idabere.utils.AppStatus;
import it.unitn.disi.lpsmt.idabere.utils.PushReceiver;
import me.pushy.sdk.Pushy;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements OnClickListener {

    public static final int IS_LOGGED_REQUEST_CODE = 100;

    private CustomersDAO customersDAO;

    private ProgressBar progressBar;
    private LinearLayout emailLoginForm;

    private String username;
    private String password;
    private String deviceToken;

    private EditText mEmailView;
    private EditText mPasswordView;

    private Button mSignInButton;
    // TODO Funzione di registrazione non implementata
    private Button mRegisterButton;
    private Context mContext;

    // Url per contattare la macchina in locale tramite il servizio ngrook
    // TODO Una volta aperto il servizio sostituire <id>
    final String ngrokUrlId = "<id>";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mContext = this;

        initViewComps();

        customersDAO = WelcomeActivity.factoryDAO.newCustomersDAO();

        mSignInButton.setOnClickListener(this);

        Pushy.listen(this);
        // Check whether the user has granted us the READ/WRITE_EXTERNAL_STORAGE permissions
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // Request both READ_EXTERNAL_STORAGE and WRITE_EXTERNAL_STORAGE so that the
            // Pushy SDK will be able to persist the device token in the external storage
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }

        Customer currentCustomer = AppSession.getInstance().getmCustomer();
        if (currentCustomer != null && currentCustomer.getId() != -1 && ! currentCustomer.getDeviceToken().isEmpty()){
            setResult(RESULT_OK);
            finish();
        }

    }

    // Instantiate layout elements
    private void initViewComps () {
        progressBar = (ProgressBar) findViewById(R.id.login_progress);

        emailLoginForm = (LinearLayout) findViewById(R.id.email_login_form);

        mEmailView = (EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);

        mEmailView.setText("professore");
        mPasswordView.setText("professore");

        mSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mRegisterButton = (Button) findViewById(R.id.email_register_button);

    }

    @Override
    public void onClick(View v) {
        if (AppStatus.getInstance(this).isOnline()) {
            username = mEmailView.getText().toString();
            password = mPasswordView.getText().toString();
            if (username.isEmpty() || password.isEmpty()){
                Toast.makeText(this, "I campi non possono essere vuoti", Toast.LENGTH_SHORT).show();
            } else {
                new RegisterForPushNotificationsAsync().execute();
            }
        } else {
            Toast.makeText(mContext, "Impossibile Connettersi", Toast.LENGTH_SHORT).show();
        }
    }

    private class AuthenticationAsyncTask extends AsyncTask<String,Void,Customer> {

        @Override
        protected Customer doInBackground(String... params) {
            Customer result = new Customer();
            result.setUsername(params[0]);
            result.setPassword(params[1]);
            result.setDeviceToken(params[2]);
            result = customersDAO.loginCustomer(result);
            if (result != null){
                Log.d("CUSTOMER", result.toString());
            }
            return result;
        }

        @Override
        protected void onPostExecute(Customer resultCustomer) {

            if (resultCustomer == null) {
                emailLoginForm.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                Toast.makeText(mContext, "Utente non trovato", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(mContext, "Autenticazione effettuata", Toast.LENGTH_SHORT).show();

                Customer currentCustomer = AppSession.getInstance().getmCustomer();
                currentCustomer.setId(resultCustomer.getId());
                currentCustomer.setUsername(resultCustomer.getUsername());
                currentCustomer.setEmail(resultCustomer.getEmail());
                currentCustomer.setPassword(password);
                currentCustomer.setCreditCards(resultCustomer.getCreditCards());

                if (resultCustomer.getPreferredItems() != null){
                    if (!resultCustomer.getPreferredItems().isEmpty()){
                        currentCustomer.setPreferredItems(resultCustomer.getPreferredItems());
                    }
                }

                // Has an active order
                if (resultCustomer.getOrder() != null && resultCustomer.getOrder().getId() != -1){
                        currentCustomer.setOrder(resultCustomer.getOrder());
                        Intent intent = new Intent();
                        intent.setClass(mContext,OrderStatusActivity.class);
                        startActivity(intent);
                    // TODO apertura della listbar activity se un ordine e' completato e l'utente ha visualizzato la schermata finale (RateOrder)
                } else {
                    setResult(Activity.RESULT_OK);
                    finish();
                }

            }

        }
    }

    private class RegisterForPushNotificationsAsync extends AsyncTask<Void, Void, Exception> {

        @Override
        protected void onPreExecute() {
            emailLoginForm.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        protected Exception doInBackground(Void... params) {
            try {
                // Assign a unique token to this device
                deviceToken = Pushy.register(getApplicationContext());
                AppSession.getInstance().getmCustomer().setDeviceToken(deviceToken);

                // Log it for debugging purposes
                Log.d("MyApp", "Pushy device token: " + deviceToken);

                // Send the token to your backend server via an HTTP GET request
                /*URL url = new URL("http://"+ngrokUrlId+".ngrok.io/notifications/register/" + deviceToken);
                Log.d("URL", url.toString());
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    Log.d("RESPONSE", in.toString());
                } finally {
                    urlConnection.disconnect();
                }*/
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
                emailLoginForm.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                Log.d("PUSHY", exc.toString());
                Toast.makeText(getApplicationContext(), "Errore di sistema. Riprova piu' tardi", Toast.LENGTH_LONG).show();
                return;
            }

            new AuthenticationAsyncTask().execute(username, password, deviceToken);

        }
    }

}

