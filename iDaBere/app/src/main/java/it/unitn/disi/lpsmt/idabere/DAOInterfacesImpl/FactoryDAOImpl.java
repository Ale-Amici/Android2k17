package it.unitn.disi.lpsmt.idabere.DAOInterfacesImpl;

import android.content.Context;

import it.unitn.disi.lpsmt.idabere.DAOIntefaces.BarsDAO;
import it.unitn.disi.lpsmt.idabere.DAOIntefaces.FactoryDAO;
import it.unitn.disi.lpsmt.idabere.DAOIntefaces.OrderItemsDAO;
import it.unitn.disi.lpsmt.idabere.DAOIntefaces.OrdersDAO;
import it.unitn.disi.lpsmt.idabere.DAOIntefaces.CustomersDAO;

/**
 * Created by giovanni on 15/05/2017.
 */

public class FactoryDAOImpl implements FactoryDAO {
    @Override
    public BarsDAO newBarsDAO() {
        return new BarsDAOImpl();
    }

    @Override
    public OrderItemsDAO newOrderItemsDAO() {
        return new OrderItemsDAOImpl();
    }

    @Override
    public OrdersDAO newOrdersDAO() {
        return new OrdesDAOImpl ();
    }

    @Override
    public CustomersDAO newCustomersDAO(Context context) { return new CustomersDAOImpl (context); }

}
