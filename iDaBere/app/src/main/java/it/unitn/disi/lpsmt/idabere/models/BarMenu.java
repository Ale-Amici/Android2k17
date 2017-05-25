package it.unitn.disi.lpsmt.idabere.models;

import java.util.ArrayList;

/**
 * Created by ale on 25/05/17.
 */

public class Menu {
    ArrayList<MenuItem> menuItemList;

    public Menu(){
        menuItemList = new ArrayList<>();
    }

    public Menu(ArrayList<MenuItem> menuItemList) {
        this.menuItemList = menuItemList;
    }

    public ArrayList<MenuItem> getMenuItemList() {
        return menuItemList;
    }

    public void setMenuItemList(ArrayList<MenuItem> menuItemList) {
        this.menuItemList = menuItemList;
    }

}
