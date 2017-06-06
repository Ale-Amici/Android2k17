package it.unitn.disi.lpsmt.idabere.models;

import java.util.ArrayList;

/**
 * Created by giovanni on 15/05/2017.
 */

public class BarMenuItem {

    private int id;
    private String name;
    private String description;
    private ArrayList<Size> sizes;
    private ArrayList<Addition> additions;
    private ArrayList<Ingredient> ingredients;
    private String category;

    public BarMenuItem(){}

    public BarMenuItem(int id, ArrayList<Size> sizes, ArrayList<Addition> additions, ArrayList<Ingredient> ingredients, String category) {
        this.id = id;
        this.sizes = sizes;
        this.additions = additions;
        this.ingredients = ingredients;
        this.category = category;
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

    public ArrayList<Size> getSizes() {
        return sizes;
    }

    public void setSizes(ArrayList<Size> sizes) {
        this.sizes = sizes;
    }

    public ArrayList<Addition> getAdditions() {
        return additions;
    }

    public void setAdditions(ArrayList<Addition> additions) {
        this.additions = additions;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }



    public Size getSizeFromId(int id){
        for(Size s: this.sizes){
            if(s.getId() == id){
                return s;
            }
        }
        return null;
    }

    public Addition getAdditionFromId(int id){
        for(Addition a: this.additions){
            if(a.getId() == id){
                return a;
            }
        }
        return null;
    }

    public int getCountPossibleChoices(){ //il numero di possibili combinazini Size-Additions
        return sizes.size() * ((int)Math.pow(2, additions.size()));
    }

    @Override
    public String toString() {
        return "BarMenuItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sizes=" + sizes +
                ", additions=" + additions +
                ", ingredients=" + ingredients +
                ", category='" + category + '\'' +
                '}';
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
