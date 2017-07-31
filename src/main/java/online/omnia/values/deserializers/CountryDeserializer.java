package online.omnia.values.deserializers;

import com.google.gson.*;
import online.omnia.values.entities.Country;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lollipop on 31.07.2017.
 */
public class CountryDeserializer implements JsonDeserializer<List<Country>>{
    @Override
    public List<Country> deserialize(JsonElement jsonElement, Type type,
                                     JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject object = jsonElement.getAsJsonObject();
        String status = object.get("status").getAsString();
        String message = object.get("message").getAsString();

        JsonElement array = object.get("data");
        List<Country> countries = new ArrayList<>();
        if (array != null) {
            JsonArray countryArray = array.getAsJsonArray();
            for (JsonElement element : countryArray) {
                countries.add(new Country(
                            element.getAsJsonObject().get("location_code").getAsString(),
                            element.getAsJsonObject().get("location").getAsString(),
                            element.getAsJsonObject().get("code").getAsString(),
                            element.getAsJsonObject().get("value").getAsString()
                        ));
            }
        }
        return countries;
    }
}
