package it.unitn.disi.lpsmt.idabere.models;

/**
 * Created by giovanni on 15/05/2017.
 */

public class BarCounter extends DeliveryPlace {

    private String counterName;

    public BarCounter () {}

    public BarCounter(int floor, String counterName) {
        super(floor);
        this.counterName = counterName;
    }

    public String getCounterName() {
        return counterName;
    }

    public void setCounterName(String counterName) {
        this.counterName = counterName;
    }

    @Override
    public String toString() {
        return counterName.toString();
    }
}
