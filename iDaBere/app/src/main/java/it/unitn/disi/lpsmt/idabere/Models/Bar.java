package it.unitn.disi.lpsmt.idabere.Models;

import android.location.Address;

import java.util.ArrayList;

/**
 * Created by giovanni on 15/05/2017.
 */

public class Bar {

    private int id;
    private String name;
    private Address address;
    private String description;
    private int distance;//from user's location (in meters)
    private Menu menu;
    private ArrayList<Event> events;
    private ArrayList<BarCounter> barCounters;
    private ArrayList<Table> tables;

    public Bar(int id, String name, Address address, Menu menu, ArrayList<Event> events, ArrayList<BarCounter> barCounters, ArrayList<Table> tables) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.menu = menu;
        this.events = events;
        this.barCounters = barCounters;
        this.tables = tables;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }

    public ArrayList<BarCounter> getBarCounters() {
        return barCounters;
    }

    public void setBarCounters(ArrayList<BarCounter> barCounters) {
        this.barCounters = barCounters;
    }

    public ArrayList<Table> getTables() {
        return tables;
    }

    public void setTables(ArrayList<Table> tables) {
        this.tables = tables;
    }
}
