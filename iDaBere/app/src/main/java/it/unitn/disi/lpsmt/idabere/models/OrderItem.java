package it.unitn.disi.lpsmt.idabere.models;

import java.util.ArrayList;

/**
 * Created by giovanni on 15/05/2017.
 */

public class OrderItem {

    private int id;
    private int quantity;
    private int rating;
    private double totalPrice;
    private Size size;
    private ArrayList<Addition> additions;
    private ArrayList<BarMenuItem> barMenuItems;
    
    public OrderItem(int id, int quantity, int rating, double totalPrice, Size size, ArrayList<Addition> additions, ArrayList<BarMenuItem> barMenuItems) {
        this.id = id;
        this.quantity = quantity;
        this.rating = rating;
        this.totalPrice = totalPrice;
        this.size = size;
        this.additions = additions;
        this.barMenuItems = barMenuItems;
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

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
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

    public ArrayList<BarMenuItem> getBarMenuItems() {
        return barMenuItems;
    }

    public void setBarMenuItems(ArrayList<BarMenuItem> barMenuItems) {
        this.barMenuItems = barMenuItems;
    }
}
