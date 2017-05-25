package it.unitn.disi.lpsmt.idabere.models;

import android.util.Log;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Type;
import java.util.Date;

/**
 * Created by giovanni on 25/05/2017.
 */

public class OpeningHour {

    private int dayOfWeek;
    private TimeOpen timeOpen;
    private double workingTime;


    public TimeOpen getOpeningHour() {
        return timeOpen;
    }

    public void setOpeningHour(TimeOpen timeOpen) {
        this.timeOpen = timeOpen;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public double getWorkingTime() {
        return workingTime;
    }

    public void setWorkingTime(double workingTime) {
        this.workingTime = workingTime;
    }

}
