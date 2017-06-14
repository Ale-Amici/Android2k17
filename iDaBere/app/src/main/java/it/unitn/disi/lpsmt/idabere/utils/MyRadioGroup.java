package it.unitn.disi.lpsmt.idabere.utils;

import android.util.Log;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.google.android.gms.games.event.Event;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.EventObject;

/**
 * Created by ale on 11/06/17.
 */

public class MyRadioGroup {
    private ArrayList<RadioButton> radioButtons;
    private RadioButton currentCheckedButton;

    private ArrayList<EventListener> listeners;//I listener che vengono triggerati quando cambia il radiobutton selezionato

    public MyRadioGroup(){
        radioButtons = new ArrayList<>();
        currentCheckedButton = null;
        listeners = new ArrayList<>();
    }

    public boolean add(final RadioButton r){
        if(r != null) {
            radioButtons.add(r);
            r.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){//quando il bottone viene settato a true deseleziono quello precedente e lancio l'evento
                        checkButton(r);
                    }
                }
            });
            return true;
        }
        return false;
    }

    public boolean initialize(){
        if(radioButtons.size() > 0) {
            currentCheckedButton = radioButtons.get(0);
            currentCheckedButton.setChecked(true);
            return true;
        }
        return false;
    }

    private void checkButton(RadioButton r){
        currentCheckedButton.setChecked(false);
        r.setChecked(true);
        currentCheckedButton = r;
        dispatchEvent(new CheckedElementChangeEvent(r,true));//lancio l'evento
    }

    public RadioButton getCurrentCheckedButton(){
        return currentCheckedButton;
    }

    /* ************************** LISTENER LOGIC *********************************** */
    public interface OnCheckedElementChangeListener extends EventListener{
        public void onCheckedElementChange(CheckedElementChangeEvent event);
    }

    public void addOnCheckedElementChangeListener(OnCheckedElementChangeListener el){
        listeners.add(el);
    }

    public void removeOnCheckedElementChangeListener(OnCheckedElementChangeListener el){
        listeners.remove(el);
    }

    protected void dispatchEvent(CheckedElementChangeEvent event){


        Object eventObject = event.getSource();
        for(int i = 0; i < listeners.size(); i ++){
            if( OnCheckedElementChangeListener.class.isInstance(listeners.get(i))){
                ((OnCheckedElementChangeListener) listeners.get(i)).onCheckedElementChange(event);
            }
        }
    }

    public class CheckedElementChangeEvent extends EventObject{
        private boolean changed;
        /**
         * Constructs a prototypical Event.
         *
         * @param source The object on which the Event initially occurred.
         * @throws IllegalArgumentException if source is null.
         */
        public CheckedElementChangeEvent(Object source, boolean changed) {
            super(source);
            this.changed = changed;
        }

        public boolean isChanged() {
            return changed;
        }
    }
    /* *********************************************************************************** */
}
