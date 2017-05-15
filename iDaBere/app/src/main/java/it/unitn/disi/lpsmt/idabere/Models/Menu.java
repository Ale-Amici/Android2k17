package it.unitn.disi.lpsmt.idabere.Models;

import java.util.ArrayList;

/**
 * Created by giovanni on 15/05/2017.
 */

public class Menu {

    private int id;
    private ArrayList<Category> categories;

    public Menu(int id, ArrayList<Category> categories) {
        this.id = id;
        this.categories = categories;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }
}
