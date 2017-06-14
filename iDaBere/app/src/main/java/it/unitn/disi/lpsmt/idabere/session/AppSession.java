package it.unitn.disi.lpsmt.idabere.session;

import it.unitn.disi.lpsmt.idabere.models.Bar;
import it.unitn.disi.lpsmt.idabere.models.Customer;
import it.unitn.disi.lpsmt.idabere.models.Order;

/**
 * Created by ale on 15/05/17.
 */

public class AppSession {

    /**************SINGLETON LOGIC*****************/
    private static AppSession instance;
    private AppSession(){}

    public static AppSession getInstance(){
        if(instance == null){
            instance = new AppSession();
            instance.setmCustomer(new Customer());//creo già un customer nuovo per la sessione
        }
        return instance;
    }
    /*****************************************/

    //Customer attualmente loggato
    private Customer mCustomer;

    //Bar attualmente selezionato
    private Bar mBar;

    public Customer getmCustomer() {
        return mCustomer;
    }

    public void setmCustomer(Customer mCustomer) {
        this.mCustomer = mCustomer;
    }

    public Bar getmBar() {
        return mBar;
    }

    public void setmBar(Bar mBar) {
        this.mBar = mBar;
    }
}
