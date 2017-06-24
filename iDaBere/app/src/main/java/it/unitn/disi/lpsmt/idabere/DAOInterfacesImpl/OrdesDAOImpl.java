package it.unitn.disi.lpsmt.idabere.DAOInterfacesImpl;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import it.unitn.disi.lpsmt.idabere.DAOIntefaces.OrdersDAO;
import it.unitn.disi.lpsmt.idabere.models.Customer;
import it.unitn.disi.lpsmt.idabere.models.Order;
import it.unitn.disi.lpsmt.idabere.utils.BackendConnection;

/**
 * Created by giovanni on 15/05/2017.
 */

public class OrdesDAOImpl implements OrdersDAO {

    private final String API_BASE_URI = "http://151.80.152.226/";
    final String AUTHENTICATION_ROUTE = "orders";

    private BackendConnection backendConnection = new BackendConnection();

    private GsonBuilder gsonBuilder = new GsonBuilder();
    private Gson gson = gsonBuilder.create();

    @Override
    public Order createOrder(Order order, Customer customer) {
        Order result = null;
        String data;

        final String LOGIN_ROUTE = "create";

        backendConnection.setBASE_URL(API_BASE_URI);
        backendConnection.buildUri();

        ArrayList<String> routes = new ArrayList<>();
        routes.add(AUTHENTICATION_ROUTE);
        routes.add(LOGIN_ROUTE);

        backendConnection.setROUTES(routes);
        backendConnection.appendRoutes();

        JsonObject jsonObject = new JsonObject();
        jsonObject.add("order", gson.toJsonTree(order));
        jsonObject.addProperty("username", customer.getUsername());
        jsonObject.addProperty("password", customer.getPassword());

        backendConnection.setPOSTParameters(jsonObject.toString());

        backendConnection.buildURL();

        data = backendConnection.connectUrlPOST();

        result = gson.fromJson(data, Order.class);

        if (result != null){
            Log.d("ORDER RESULT", result.toString());
        }

        return result;
    }
}
