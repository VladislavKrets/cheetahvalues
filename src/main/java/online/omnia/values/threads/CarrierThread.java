package online.omnia.values.threads;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import online.omnia.values.HttpMethodsUtils;
import online.omnia.values.deserializers.CarrierDeserializer;
import online.omnia.values.entities.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * Created by lollipop on 31.07.2017.
 */
public class CarrierThread implements Runnable{
    private List<CountryCarrierEntity> carrierEntities;
    private CountDownLatch countDownLatch;
    private Country country;
    private String token;
    private String url;

    public CarrierThread(List<CountryCarrierEntity> carrierEntities, CountDownLatch countDownLatch,
                         Country country, String token, String url) {
        this.carrierEntities = carrierEntities;
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
        List<Carrier> carriers = getCarriers(url, country.getLocationCode(), httpMethodsUtils, headers);
        carrierEntities.add(new CountryCarrierEntity(carriers, country));
        countDownLatch.countDown();
    }
    private List<Carrier> getCarriers(String url, String locationCode, HttpMethodsUtils methodsUtils, Map<String, String> headers) {
        String answer = methodsUtils.getMethod(url + "dict/carrier?location_code=" + locationCode, headers);
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(List.class, new CarrierDeserializer());
        Gson gson = builder.create();
        return gson.fromJson(answer, List.class);
    }
}
