package it.unitn.disi.lpsmt.idabere.DAOIntefaces;

import it.unitn.disi.lpsmt.idabere.models.Customer;

/**
 * Created by giovanni on 14/05/2017.
 */

public interface CustomersDAO {
    // Authentication method /authentication/login
    Customer loginCustomer (Customer customer);

    // Preferreds method /users/:id/preferreds/action/:itemId
    void AddPreferred (int customerId, int itemId);
    void RemovePreferred (int customerId, int itemId);
}
