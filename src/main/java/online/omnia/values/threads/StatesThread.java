package online.omnia.values.threads;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import online.omnia.values.HttpMethodsUtils;
import online.omnia.values.deserializers.StateDeserializer;
import online.omnia.values.entities.Country;
import online.omnia.values.entities.CountryStateEntity;
import online.omnia.values.entities.State;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * Created by lollipop on 31.07.2017.
 */
public class StatesThread implements Runnable{
    private List<CountryStateEntity> countryStateEntities;
    private CountDownLatch countDownLatch;
    private Country country;
    private String token;
    private String url;

    public StatesThread(List<CountryStateEntity> countryStateEntities,
                        CountDownLatch countDownLatch, Country country, String token, String url) {
        this.countryStateEntities = countryStateEntities;
        this.countDownLatch = countDownLatch;
        this.country = country;
        this.token = token;
        this.url = url;
    }

    @Override
    public void run() {
        HttpMethodsUtils httpMethodsUtils = new HttpMethodsUtils();
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Accept", "application/json,application/x.orion.v1+json");
        headers.put("Authorization", "Bearer " + token);
        List<State> states = getStates(url, country.getLocationCode(), httpMethodsUtils, headers);
        countryStateEntities.add(new CountryStateEntity(states, country));
        countDownLatch.countDown();
    }
    private List<State> getStates(String url, String locationCode, HttpMethodsUtils methodsUtils, Map<String, String> headers) {
        String answer = methodsUtils.getMethod(url + "dict/state?location_code=" + locationCode, headers);
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(List.class, new StateDeserializer());
        Gson gson = builder.create();
        return gson.fromJson(answer, List.class);
    }
}
