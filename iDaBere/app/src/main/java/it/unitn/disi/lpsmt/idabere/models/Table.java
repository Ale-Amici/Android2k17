package it.unitn.disi.lpsmt.idabere.models;

/**
 * Created by giovanni on 15/05/2017.
 */

public class Table extends DeliveryPlace {

    private int tableNumber;

    public Table () {}

    public Table(int floor, int tableNumber) {
        super(floor);
        this.tableNumber = tableNumber;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }
}
