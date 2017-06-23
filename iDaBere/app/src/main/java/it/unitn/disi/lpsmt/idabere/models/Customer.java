package it.unitn.disi.lpsmt.idabere.models;

import java.util.ArrayList;

/**
 * Created by giovanni on 15/05/2017.
 */

public class Customer {

    private int id;
    private String username;
    private int age;
    private String email;
    private String password;
    private Order order;
    private ArrayList<BarMenuItem> preferredItems;
    private ArrayList<CreditCard> creditCards;

    public Customer(){
        id = -1;
        username = "unknown";
        age = -1;
        order = new Order();
        creditCards = new ArrayList<>();
    }

    public Customer(int id, String username, int age, String email, String password, Order order, ArrayList<BarMenuItem> preferredItems) {
        this.id = id;
        this.username = username;
        this.age = age;
        this.email = email;
        this.password = password;
        this.order = order;
        this.preferredItems = preferredItems;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public ArrayList<BarMenuItem> getPreferredItems() {
        return preferredItems;
    }

    public void setPreferredItems(ArrayList<BarMenuItem> preferredItems) {
        this.preferredItems = preferredItems;
    }

    public ArrayList<CreditCard> getCreditCards() {
        return creditCards;
    }

    public void setCreditCards(ArrayList<CreditCard> creditCards) {
        this.creditCards = creditCards;
    }
}
