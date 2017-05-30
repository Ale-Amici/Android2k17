package it.unitn.disi.lpsmt.idabere.models;

import java.util.ArrayList;

/**
 * Created by giovanni on 15/05/2017.
 */

public class OrderItem {

    private int id;
    private int quantity;
    private int rating;
    private double singleItemPrice;
    private Size size;
    private ArrayList<Addition> additions;
    private BarMenuItem barMenuItem;

    public OrderItem(int quantity, double singleItemPrice, Size size, ArrayList<Addition> additions, BarMenuItem barMenuItem){
        this.id = -1;
        this.rating = -1;
        this.quantity = quantity;
        this.singleItemPrice = singleItemPrice;
        this.size = size;
        this.additions = additions;
        this.barMenuItem = barMenuItem;

    }

    public OrderItem(int id, int quantity, int rating, double singleItemPrice, Size size, ArrayList<Addition> additions, BarMenuItem barMenuItems) {
        this.id = id;
        this.quantity = quantity;
        this.rating = rating;
        this.singleItemPrice = singleItemPrice;
        this.size = size;
        this.additions = additions;
        this.barMenuItem = barMenuItem;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public double getSingleItemPrice() {
        return singleItemPrice;
    }

    public void setSingleItemPrice(double singleItemPrice) {
        this.singleItemPrice = singleItemPrice;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public ArrayList<Addition> getAdditions() {
        return additions;
    }

    public void setAdditions(ArrayList<Addition> additions) {
        this.additions = additions;
    }

    public BarMenuItem getBarMenuItem() {
        return barMenuItem;
    }

    public void setBarMenuItem(BarMenuItem barMenuItem) {
        this.barMenuItem = barMenuItem;
    }
}
