package it.unitn.disi.lpsmt.idabere.DAOIntefaces;

/**
 * Created by giovanni on 14/05/2017.
 */

public interface FactoryDAO {

    BarsDAO newBarsDAO ();
    OrderItemsDAO newOrderItemsDAO ();
    OrdersDAO newOrdersDAO ();
    UsersDAO newUsersDAO ();

}
