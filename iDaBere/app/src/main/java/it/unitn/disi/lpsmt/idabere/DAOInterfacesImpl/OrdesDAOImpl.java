package it.unitn.disi.lpsmt.idabere.DAOInterfacesImpl;

import android.content.Context;

import it.unitn.disi.lpsmt.idabere.DAOIntefaces.OrdersDAO;
import it.unitn.disi.lpsmt.idabere.models.Order;
import it.unitn.disi.lpsmt.idabere.utils.BackendConnection;

/**
 * Created by giovanni on 15/05/2017.
 */

public class OrdesDAOImpl implements OrdersDAO {

    private final String API_BASE_URI = "http://151.80.152.226/";
    final String AUTHENTICATION_ROUTE = "orders";

    private BackendConnection backendConnection = new BackendConnection();

    @Override
    public Order createOrder(Order order) {
        Order result = null;
        return result;
    }
}
