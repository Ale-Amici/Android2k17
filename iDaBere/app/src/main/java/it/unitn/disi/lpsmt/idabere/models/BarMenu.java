package it.unitn.disi.lpsmt.idabere.models;

import android.util.Log;

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
    public BarMenuItem getBarMenuItemById(int id) {
        BarMenuItem result = null;
        Log.d("IDPARAM",Integer.toString(id));
        Log.d("IDPARAM",Integer.toString(barMenuItemList.size()));
        for (int i = 0; i < barMenuItemList.size(); i++) {
            Log.d("IDPARAM",Integer.toString(barMenuItemList.get(i).getId()));
            if (barMenuItemList.get(i).getId() == id){
                result = barMenuItemList.get(i);
            }
        }
        return result;
    }
}
