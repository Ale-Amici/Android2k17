package it.unitn.disi.lpsmt.idabere.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import it.unitn.disi.lpsmt.idabere.DAOIntefaces.FactoryDAO;
import it.unitn.disi.lpsmt.idabere.DAOInterfacesImpl.FactoryDAOImpl;
import it.unitn.disi.lpsmt.idabere.R;
import it.unitn.disi.lpsmt.idabere.models.Order;
import it.unitn.disi.lpsmt.idabere.session.AppSession;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    public static FactoryDAO factoryDAO = new FactoryDAOImpl();


    private Button authenticateButton;
    private Button orderButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        initComps();

        authenticateButton.setOnClickListener(this);
        orderButton.setOnClickListener(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Intent intent = new Intent();
        switch (resultCode) {
            case RESULT_OK :
                intent.setClass(this, ListBarActivity.class);
                startActivity(intent);
                break;
            default:
                //Toast.makeText(this, "Autenticazione", Toast.LENGTH_SHORT).show();
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void initComps () {
        authenticateButton = (Button) findViewById(R.id.welcome_login_button);
        orderButton = (Button) findViewById(R.id.welcome_proceed_button);
    }

//    private boolean hasActiveOrder() {
//        boolean result = false;
//        Order currentOrder = AppSession.getInstance().getmCustomer().getOrder();
//        if (currentOrder != null) {
//            if (currentOrder.getId() != -1){
//                result = true;
//            }
//        }
//        return result;
//    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Intent intent = new Intent();
        switch (id) {
            case R.id.welcome_login_button :
                if (AppSession.getInstance().getmCustomer().getId() == -1){
                    intent.setClass(this, LoginActivity.class);
                    startActivityForResult(intent, LoginActivity.IS_LOGGED_REQUEST_CODE);
                } else {
                    intent.setClass(this, ListBarActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.welcome_proceed_button :
                intent.setClass(this, ListBarActivity.class);
                startActivity(intent);
                break;
        }
    }

    private class CheckCurrentOrderAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {

            return null;
        }
    }

}
