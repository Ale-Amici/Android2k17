package it.unitn.disi.lpsmt.idabere.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
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
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import it.unitn.disi.lpsmt.idabere.DAOIntefaces.CustomersDAO;
import it.unitn.disi.lpsmt.idabere.DAOIntefaces.FactoryDAO;
import it.unitn.disi.lpsmt.idabere.DAOInterfacesImpl.CustomersDAOImpl;
import it.unitn.disi.lpsmt.idabere.DAOInterfacesImpl.FactoryDAOImpl;
import it.unitn.disi.lpsmt.idabere.R;
import it.unitn.disi.lpsmt.idabere.models.Customer;
import it.unitn.disi.lpsmt.idabere.session.AppSession;
import it.unitn.disi.lpsmt.idabere.utils.AppStatus;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements OnClickListener {

    private CustomersDAO customersDAO;

    private String username;
    private String password;

    private EditText mEmailView;
    private EditText mPasswordView;

    private Button mSignInButton;
    // TODO Funzione di registrazione non implementata
    private Button mRegisterButton;
    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mContext = this;

        initViewComps();

        customersDAO = ListBarActivity.factoryDAO.newCustomersDAO();

        mSignInButton.setOnClickListener(this);

    }

    // Instantiate layout elements
    private void initViewComps () {
        mEmailView = (EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);

        mEmailView.setText("giulia");
        mPasswordView.setText("giulia");

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
                new AuthenticationAsyncTask().execute(username, password);
            }
        } else {
            Toast.makeText(mContext, "Impossibile Connettersi", Toast.LENGTH_SHORT).show();
        }
    }

    private class AuthenticationAsyncTask extends AsyncTask<String,Void,Customer> {
        @Override
        protected Customer doInBackground(String... params) {
            Customer result = null;
            result = customersDAO.loginCustomer(params[0], params[1]);
            return result;
        }

        @Override
        protected void onPostExecute(Customer resultCustomer) {
            if (resultCustomer == null) {
                Toast.makeText(mContext, "Utente non trovato", Toast.LENGTH_SHORT).show();
            } else {
                Customer currentCustomer = AppSession.getInstance().getmCustomer();
                currentCustomer.setId(resultCustomer.getId());
                currentCustomer.setUsername(resultCustomer.getUsername());
                currentCustomer.setEmail(resultCustomer.getEmail());
                currentCustomer.setPassword(resultCustomer.getPassword());
                currentCustomer.setCreditCards(resultCustomer.getCreditCards());

                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        }
    }

}

