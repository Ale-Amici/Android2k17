package it.unitn.disi.lpsmt.idabere.Models;

/**
 * Created by giovanni on 15/05/2017.
 */

public class Ingredient {

    private int id;
    private String name;

    public Ingredient(int id, String name) {
        this.id = id;
        this.name = name;
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
}
