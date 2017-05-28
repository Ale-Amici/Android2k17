package it.unitn.disi.lpsmt.idabere.models;

import java.util.ArrayList;

/**
 * Created by ale on 25/05/17.
 */

public class BarMenu {
    ArrayList<BarMenuItem> barMenuItemList;

    public BarMenu(){
        barMenuItemList = new ArrayList<>();
    }

    public BarMenu(ArrayList<BarMenuItem> barMenuItemList) {
        this.barMenuItemList = barMenuItemList;
    }

    public ArrayList<BarMenuItem> getBarMenuItemList() {
        return barMenuItemList;
    }

    public void setBarMenuItemList(ArrayList<BarMenuItem> barMenuItemList) {
        this.barMenuItemList = barMenuItemList;
    }

    public BarMenuItem getBarMenuItemFromId(int id){
        for(BarMenuItem item: barMenuItemList){
            if(item.getId() == id){
                return item;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "BarMenu{" +
                "barMenuItemList=" + barMenuItemList +
                '}';
    }
}
