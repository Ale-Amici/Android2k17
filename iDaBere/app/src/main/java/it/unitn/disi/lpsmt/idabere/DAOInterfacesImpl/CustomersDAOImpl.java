package it.unitn.disi.lpsmt.idabere.DAOInterfacesImpl;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import it.unitn.disi.lpsmt.idabere.DAOIntefaces.CustomersDAO;
import it.unitn.disi.lpsmt.idabere.deserializer.BooleanSerializer;
import it.unitn.disi.lpsmt.idabere.models.Customer;
import it.unitn.disi.lpsmt.idabere.utils.BackendConnection;

/**
 * Created by giovanni on 15/05/2017.
 */

public class CustomersDAOImpl implements CustomersDAO {

    private final String API_BASE_URI = "http://151.80.152.226/";

    private final String USERS_ROUTE = "users";
    private final String AUTHENTICATION_ROUTE = "authentication";
    private final String PREFERREDS_ROUTE = "preferreds";

    private GsonBuilder gsonBuilder = new GsonBuilder();
    private BooleanSerializer booleanSerializer;
    private Gson gson;

    private BackendConnection backendConnection = new BackendConnection();

    // /users/:id/preferreds/action/:itemId

    @Override
    public void AddPreferred(int customerId, int itemId) {
        gson = gsonBuilder.create();
        final String ADD_ROUTE = "add";

        backendConnection.setBASE_URL(API_BASE_URI);
        backendConnection.buildUri();

        ArrayList<String> routes = new ArrayList<>();
        routes.add(USERS_ROUTE);
        routes.add(Integer.toString(customerId));
        routes.add(PREFERREDS_ROUTE);
        routes.add(ADD_ROUTE);
        routes.add(Integer.toString(itemId));

        backendConnection.setROUTES(routes);
        backendConnection.appendRoutes();

        backendConnection.buildURL();

        backendConnection.connectUrlPOST();

    }

    @Override
    public void RemovePreferred(int customerId, int itemId) {
        gson = gsonBuilder.create();
        final String ADD_ROUTE = "remove";

        backendConnection.setBASE_URL(API_BASE_URI);
        backendConnection.buildUri();

        ArrayList<String> routes = new ArrayList<>();
        routes.add(USERS_ROUTE);
        routes.add(Integer.toString(customerId));
        routes.add(PREFERREDS_ROUTE);
        routes.add(ADD_ROUTE);
        routes.add(Integer.toString(itemId));

        backendConnection.setROUTES(routes);
        backendConnection.appendRoutes();

        backendConnection.buildURL();

        backendConnection.connectUrlPOST();
    }

    @Override
    public Customer loginCustomer(Customer customer) {
        Customer result;
        String data;

        booleanSerializer = new BooleanSerializer();
        gsonBuilder.registerTypeAdapter(Boolean.class, booleanSerializer);
        gsonBuilder.registerTypeAdapter(boolean.class, booleanSerializer);

        gson = gsonBuilder.create();

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
