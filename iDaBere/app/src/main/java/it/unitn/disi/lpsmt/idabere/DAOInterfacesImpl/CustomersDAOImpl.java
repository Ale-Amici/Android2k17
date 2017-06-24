package it.unitn.disi.lpsmt.idabere.DAOInterfacesImpl;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import it.unitn.disi.lpsmt.idabere.DAOIntefaces.CustomersDAO;
import it.unitn.disi.lpsmt.idabere.models.Customer;
import it.unitn.disi.lpsmt.idabere.utils.BackendConnection;

/**
 * Created by giovanni on 15/05/2017.
 */

public class CustomersDAOImpl implements CustomersDAO {

    private final String API_BASE_URI = "http://151.80.152.226/";
    final String AUTHENTICATION_ROUTE = "authentication";

    private GsonBuilder gsonBuilder = new GsonBuilder();
    Gson gson = gsonBuilder.create();

    private BackendConnection backendConnection = new BackendConnection();

    @Override
    public Customer loginCustomer(Customer customer) {
        Customer result;
        String data;

        final String LOGIN_ROUTE = "login";

        backendConnection.setBASE_URL(API_BASE_URI);
        backendConnection.buildUri();

        ArrayList<String> routes = new ArrayList<>();
        routes.add(AUTHENTICATION_ROUTE);
        routes.add(LOGIN_ROUTE);

        backendConnection.setROUTES(routes);
        backendConnection.appendRoutes();

        backendConnection.setPOSTParameters(gson.toJson(customer));

        backendConnection.buildURL();

        data = backendConnection.connectUrlPOST();


        result = gson.fromJson(data, Customer.class);

        if (result != null){
            Log.d("CUSTOMER", result.toString());
        }

        return result;
    }

}
