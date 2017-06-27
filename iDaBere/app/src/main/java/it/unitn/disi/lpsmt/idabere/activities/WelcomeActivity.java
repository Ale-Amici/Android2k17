package it.unitn.disi.lpsmt.idabere.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import it.unitn.disi.lpsmt.idabere.DAOIntefaces.FactoryDAO;
import it.unitn.disi.lpsmt.idabere.DAOInterfacesImpl.FactoryDAOImpl;
import it.unitn.disi.lpsmt.idabere.R;
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

    private void initComps () {
        authenticateButton = (Button) findViewById(R.id.welcome_login_button);
        orderButton = (Button) findViewById(R.id.welcome_proceed_button);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Intent intent = new Intent();
        switch (id) {
            case R.id.welcome_login_button :
                if (AppSession.getInstance().getmCustomer().getId() == -1){
                    intent.setClass(this, LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.welcome_proceed_button :
                intent.setClass(this, ListBarActivity.class);
                startActivity(intent);
                break;
        }
    }
}
