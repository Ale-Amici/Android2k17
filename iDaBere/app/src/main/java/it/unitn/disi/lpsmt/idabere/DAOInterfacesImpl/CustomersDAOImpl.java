package it.unitn.disi.lpsmt.idabere.DAOInterfacesImpl;

import android.content.Context;

import java.util.ArrayList;

import it.unitn.disi.lpsmt.idabere.DAOIntefaces.CustomersDAO;
import it.unitn.disi.lpsmt.idabere.activities.ListBarActivity;
import it.unitn.disi.lpsmt.idabere.models.Customer;
import it.unitn.disi.lpsmt.idabere.utils.BackendConnection;
import it.unitn.disi.lpsmt.idabere.utils.RequestQueue;

/**
 * Created by giovanni on 15/05/2017.
 */

public class CustomersDAOImpl implements CustomersDAO {

//    private final String API_BASE_URI = "http://151.80.152.226/";
    private final String API_BASE_URI = "http://127.0.0.1:8080/";
    final String AUTHENTICATION_ROUTE = "authentication";

    private Context mContext;

    private BackendConnection backendConnection;

    public CustomersDAOImpl (Context context) {
        mContext = context;
        backendConnection = new BackendConnection(RequestQueue.getInstance(mContext).getRequestQueue());
    }

    @Override
    public Customer loginCustomer(String username, String password) {
        Customer result = null;
        String data;

        final String LOGING_ROUTE = "login";

        backendConnection.setBASE_URI(API_BASE_URI);
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

//        GsonBuilder gsonBuilder = new GsonBuilder();
//
//        Type collectionType = new TypeToken<ArrayList<Bar>>(){}.getType();
//
//        gsonBuilder.registerTypeAdapter(TimeOpen.class, new TimeOpenDeserializer());
//
//        Gson gson = gsonBuilder.create();
//
//        result = gson.fromJson(data, collectionType);

        return result;
    }

}
