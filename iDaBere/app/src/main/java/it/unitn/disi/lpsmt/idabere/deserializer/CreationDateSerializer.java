package it.unitn.disi.lpsmt.idabere.deserializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by giovanni on 25/06/2017.
 */

public class CreationDateSerializer implements JsonSerializer<String> {
    @Override
    public JsonElement serialize(String src, Type typeOfSrc, JsonSerializationContext context) {
        return null;
    }
}
