package it.unitn.disi.lpsmt.idabere.utils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import it.unitn.disi.lpsmt.idabere.models.TimeOpen;


/**
 * Created by giovanni on 25/05/2017.
 */

public class TimeOpenDeserializer implements JsonDeserializer<TimeOpen> {
    @Override
    public TimeOpen deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        DateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        Date date = null;
        TimeOpen result = new TimeOpen();

        try {
           date  = sdf.parse(json.getAsJsonPrimitive().getAsString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        result.setTimeOpen(date);

        return result;
    }
}
