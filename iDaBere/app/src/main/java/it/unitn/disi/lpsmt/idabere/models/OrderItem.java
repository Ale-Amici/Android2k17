package it.unitn.disi.lpsmt.idabere.models;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

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

    @Override
    public boolean equals(Object other){
        if(other == null) return false;
        if(other == this) return true;
        if(!(other instanceof OrderItem)) return false;

        OrderItem orderItem2 = (OrderItem) other;
        if(orderItem2.getSize().getId() != this.getSize().getId()) return false;
        if(orderItem2.getAdditions().size() != this.getAdditions().size()) return false;
        Collections.sort(orderItem2.getAdditions());
        Collections.sort(this.getAdditions());
        for(int i = 0; i < this.getAdditions().size(); i++){
            if(this.getAdditions().get(i).getId() != orderItem2.getAdditions().get(i).getId()) return false;
        }
        return true;
    }

    public String getAdditionsAsString() {
        String result = "";
        int i;
        for (i=0; i < additions.size()-1; i++) {
            result += additions.get(i).getName().toString() + ", ";
        }
        result+= additions.get(i).getName();
        return result;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", rating=" + rating +
                ", singleItemPrice=" + singleItemPrice +
                ", size=" + size +
                ", additions=" + additions +
                ", barMenuItem=" + barMenuItem +
                '}';
    }
}
