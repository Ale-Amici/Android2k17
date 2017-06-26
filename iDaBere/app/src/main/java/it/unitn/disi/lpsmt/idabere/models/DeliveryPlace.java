package it.unitn.disi.lpsmt.idabere.models;

/**
 * Created by giovanni on 15/05/2017.
 */

public abstract class DeliveryPlace {

    private int floor;
    private int id;

    public DeliveryPlace () {}

    public DeliveryPlace(int floor) {
        this.floor = floor;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public String getName() {return "";};

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
