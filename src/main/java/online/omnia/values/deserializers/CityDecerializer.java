package online.omnia.values.deserializers;

import com.google.gson.*;
import online.omnia.values.entities.City;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lollipop on 31.07.2017.
 */
public class CityDecerializer implements JsonDeserializer<List<City>>{
    @Override
    public List<City> deserialize(JsonElement jsonElement, Type type,
                                  JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject object = jsonElement.getAsJsonObject();
        String status = object.get("status").getAsString();
        String message = object.get("message").getAsString();
        System.out.println("Getting city");
        System.out.println(status + " " + message);
        List<City> cities = new ArrayList<>();
        JsonElement array = object.get("data");
        if (array != null) {
            JsonArray cityArray = array.getAsJsonArray();
            for (JsonElement element : cityArray) {
                cities.add(new City(
                        element.getAsJsonObject().get("city_code").getAsString(),
                        element.getAsJsonObject().get("city").getAsString(),
                        element.getAsJsonObject().get("state_code").getAsString(),
                        element.getAsJsonObject().get("state").getAsString(),
                        element.getAsJsonObject().get("location_code").getAsString()
                ));
            }
        }
        return cities;
    }
}
