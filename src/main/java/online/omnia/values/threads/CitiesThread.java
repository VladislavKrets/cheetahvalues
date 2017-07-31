package online.omnia.values.threads;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import online.omnia.values.HttpMethodsUtils;
import online.omnia.values.deserializers.CityDecerializer;
import online.omnia.values.entities.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * Created by lollipop on 31.07.2017.
 */
public class CitiesThread implements Runnable{
    private List<CountryCityEntity> countryCityEntities;
    private CountDownLatch countDownLatch;
    private Country country;
    private String token;
    private String url;

    public CitiesThread(List<CountryCityEntity> countryCityEntities, CountDownLatch countDownLatch,
                        Country country, String token, String url) {
        this.countryCityEntities = countryCityEntities;
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
        List<City> cities = getCities(url, country.getLocationCode(), httpMethodsUtils, headers);
        countryCityEntities.add(new CountryCityEntity(cities, country));
        countDownLatch.countDown();
    }
    private List<City> getCities(String url, String locationCode,
                                 HttpMethodsUtils methodsUtils, Map<String, String> headers) {
        String answer = methodsUtils.getMethod(url + "dict/city?location_code=" + locationCode, headers);
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(List.class, new CityDecerializer());
        Gson gson = builder.create();
        return gson.fromJson(answer, List.class);
    }
}
