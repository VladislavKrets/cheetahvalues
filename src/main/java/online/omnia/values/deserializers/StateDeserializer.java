package online.omnia.values.deserializers;

import com.google.gson.*;
import online.omnia.values.entities.State;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lollipop on 31.07.2017.
 */
public class StateDeserializer implements JsonDeserializer<List<State>>{
    @Override
    public List<State> deserialize(JsonElement jsonElement, Type type,
                                   JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject object = jsonElement.getAsJsonObject();
        System.out.println("getting state list");
        String status = object.get("status").getAsString();
        String message = object.get("message").getAsString();

        System.out.println(status + " " + message);
        List<State> states = new ArrayList<State>();
        JsonElement array = object.get("data");
        if (array != null) {
            JsonArray stateArray = array.getAsJsonArray();
            for (JsonElement element : stateArray) {
                states.add(new State(
                        element.getAsJsonObject().get("location_code").getAsString(),
                        element.getAsJsonObject().get("state_code").getAsString(),
                        element.getAsJsonObject().get("state").getAsString()
                ));
            }
        }
        return states;
    }
}
