package it.unitn.disi.lpsmt.idabere.models;

/**
 * Created by giovanni on 15/05/2017.
 */

public class BarTable extends DeliveryPlace {

    private int tableNumber;

    public BarTable() {}

    public BarTable(int floor, int tableNumber) {
        super(floor);
        this.tableNumber = tableNumber;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    @Override
    public String toString() {
        return Integer.toString(tableNumber);
    }

    @Override
    public String getName() {
        return "TAVOLO";
    }
}

