package it.unitn.disi.lpsmt.idabere.models;

import java.util.ArrayList;

/**
 * Created by giovanni on 15/05/2017.
 */

public class Order {

    // TODO Modellazione della posizione in coda
    // TODO Modellazione del creation date
    private int id;
    private enum status {PAUSED, IN_PROCESS};
    private boolean isPaid;
    private ArrayList<OrderItem> orderItems;

    public Order(){
        id = -1;
        isPaid = false;
        orderItems = new ArrayList<>();
    }
    public Order(int id, boolean isPaid, ArrayList<OrderItem> orderItems) {
        this.id = id;
        this.isPaid = isPaid;
        this.orderItems = orderItems;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }

    public ArrayList<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(ArrayList<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public ArrayList<OrderItem> getOrderListFromBarMenuItemId(int barMenuItemId){
        ArrayList<OrderItem> ordItems = new ArrayList<>();
        for(OrderItem item: orderItems){
            if(item.getBarMenuItem().getId() == barMenuItemId){
                ordItems.add(item);
            }
        }
        return ordItems;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", isPaid=" + isPaid +
                ", orderItems=" + orderItems +
                '}';
    }
}
