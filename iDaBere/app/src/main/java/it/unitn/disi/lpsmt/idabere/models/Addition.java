package it.unitn.disi.lpsmt.idabere.models;

import android.support.annotation.NonNull;

/**
 * Created by giovanni on 15/05/2017.
 */

public class Addition implements Comparable{

    private int id;
    private String name;
    private double price;

    public Addition(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    @Override
    public int compareTo(@NonNull Object o) {
        if(this.equals(o)) return 0;
        Addition otherAddition = (Addition) o;
        if(this.getId() < otherAddition.getId()) return -1;
        if(this.getId() > otherAddition.getId()) return 1;
        return 0;
    }
}
