package online.omnia.values.deserializers;

import com.google.gson.*;
import online.omnia.values.entities.Carrier;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lollipop on 31.07.2017.
 */
public class CarrierDeserializer implements JsonDeserializer<List<Carrier>>{
    @Override
    public List<Carrier> deserialize(JsonElement jsonElement, Type type,
                                     JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject object = jsonElement.getAsJsonObject();
        System.out.println("getting carrier list");
        String status = object.get("status").getAsString();
        String message = object.get("message").getAsString();
        System.out.println(status + " " + message);
        JsonElement array = object.get("data");
        List<Carrier> carriers = new ArrayList<>();
        if (array != null) {
            JsonArray carrierArray = array.getAsJsonArray();
            for (JsonElement element : carrierArray) {
                carriers.add(new Carrier(
                        element.getAsJsonObject().get("value").getAsString()
                ));
            }
        }
        return carriers;
    }
}
