package it.unitn.disi.lpsmt.idabere.utils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import it.unitn.disi.lpsmt.idabere.models.DeliveryPlace;

/**
 * Created by giovanni on 01/06/2017.
 */

public class DeliveryPlaceDeserializer implements JsonDeserializer<DeliveryPlace> {

    Map<String, Class<? extends DeliveryPlace>> dataTypeRegistry = new HashMap<String, Class<? extends DeliveryPlace>>();

    public void registerDeliveryPlace(String jsonElementName, Class<? extends DeliveryPlace> javaType)
    {
        dataTypeRegistry.put(jsonElementName, javaType);
    }

    @Override
    public DeliveryPlace deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        JsonObject jsonObject = json.getAsJsonObject();
        for (String elementName : dataTypeRegistry.keySet())
        {
            if (jsonObject.has(elementName))
            {
                Class<? extends DeliveryPlace> dataType = dataTypeRegistry.get(elementName);
                return context.deserialize(jsonObject, dataType);
            }
        }
        throw new RuntimeException("Oops");
    }

}
