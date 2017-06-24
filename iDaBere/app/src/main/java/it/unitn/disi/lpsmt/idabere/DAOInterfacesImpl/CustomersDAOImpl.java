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

    private BackendConnection backendConnection = new BackendConnection();

    @Override
    public Customer loginCustomer(String username, String password) {
        Customer result = null;
        String data;

        final String LOGING_ROUTE = "login";

        backendConnection.setBASE_URL(API_BASE_URI);
        backendConnection.buildUri();

        ArrayList<String> routes = new ArrayList<>();
        routes.add(AUTHENTICATION_ROUTE);
        routes.add(LOGING_ROUTE);

        backendConnection.setROUTES(routes);
        backendConnection.appendRoutes();

        ArrayList<String> parameters = new ArrayList<>();
        parameters.add("username");
        parameters.add("password");
        backendConnection.setPARAMETERS(parameters);

        ArrayList<String> parameters_values = new ArrayList<>();
        parameters_values.add(username);
        parameters_values.add(password);
        backendConnection.setPARAMETERS_VALUES(parameters_values);

        backendConnection.buildURL();

        data = backendConnection.connectUrlPOST();

        GsonBuilder gsonBuilder = new GsonBuilder();

        Gson gson = gsonBuilder.create();

        result = gson.fromJson(data, Customer.class);

        if (result != null){
            Log.d("CUSTOMER", result.toString());
        }

        return result;
    }

}
