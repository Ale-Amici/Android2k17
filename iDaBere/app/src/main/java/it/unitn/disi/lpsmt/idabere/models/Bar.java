package it.unitn.disi.lpsmt.idabere.models;

import android.location.Address;

import java.util.ArrayList;

import it.unitn.disi.lpsmt.idabere.models.BarCounter;
import it.unitn.disi.lpsmt.idabere.models.BarMenu;
import it.unitn.disi.lpsmt.idabere.models.Event;
import it.unitn.disi.lpsmt.idabere.models.OpeningHour;
import it.unitn.disi.lpsmt.idabere.models.Table;

/**
 * Created by giovanni on 15/05/2017.
 */

public class Bar {

    private String name;
    private int id;
    private String description;
    private String address;
    private double latitude;
    private double longitude;
    private ArrayList<OpeningHour> openingHours;
    private ArrayList<Event> events;
    private ArrayList<DeliveryPlace> deliveryPlaces;
    private BarMenu barMenu;
    private double distance;//from user's location (in meters)

    public Bar(){}

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getDistance() {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BarMenu getBarMenu() {
        return barMenu;
    }

    public void setBarMenu(BarMenu barMenu) {
        this.barMenu = barMenu;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }

    public ArrayList<DeliveryPlace> getDeliveryPlaces() {
        return deliveryPlaces;
    }

    public void setDeliveryPlaces(ArrayList<DeliveryPlace> deliveryPlaces) {
        this.deliveryPlaces = deliveryPlaces;
    }

    public ArrayList<OpeningHour> getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(ArrayList<OpeningHour> openingHours) {
        this.openingHours = openingHours;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
