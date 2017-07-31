package online.omnia.values.deserializers;

import com.google.gson.*;
import online.omnia.values.entities.Token;

import java.lang.reflect.Type;

/**
 * Created by lollipop on 31.07.2017.
 */
public class TokenDeserializer implements JsonDeserializer<Token>{
    @Override
    public Token deserialize(JsonElement jsonElement, Type type,
                             JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject object = jsonElement.getAsJsonObject();
        System.out.println("Getting token");
        String status = object.get("status").getAsString();
        String message = object.get("message").getAsString();
        System.out.println(status + " " + message);
        JsonElement tokenElement = object.get("data");
        if (tokenElement != null) {
            Token token = new Token(
                    tokenElement.getAsJsonObject().get("access_token").getAsString(),
                    tokenElement.getAsJsonObject().get("token_type").getAsString(),
                    tokenElement.getAsJsonObject().get("expires_in").getAsString()
            );
            return token;
        }
        return null;
    }
}
