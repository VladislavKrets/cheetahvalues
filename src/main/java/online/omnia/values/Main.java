package online.omnia.values;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import online.omnia.values.deserializers.CarrierDeserializer;
import online.omnia.values.deserializers.CountryDeserializer;
import online.omnia.values.deserializers.StateDeserializer;
import online.omnia.values.deserializers.TokenDeserializer;
import online.omnia.values.entities.*;
import online.omnia.values.threads.CarrierThread;
import online.omnia.values.threads.CitiesThread;
import online.omnia.values.threads.StatesThread;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by lollipop on 31.07.2017.
 */
public class Main {
    public static void main(String[] args) {
        Main main = new Main();
        String url = "https://api.ori.cmcm.com/";
        HttpMethodsUtils methodsUtils = new HttpMethodsUtils();
        Token token = main.getToken(url, methodsUtils);

        if (token == null) return;

        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Accept", "application/json,application/x.orion.v1+json");
        headers.put("Authorization", "Bearer " + token.getAccessToken());
        List<Country> countries = main.getCountries(url, methodsUtils, headers);

        //main.handleStates(url, token, countries);
        //main.handleCarriers(url, token, countries);
        main.handleCities(url, token, countries);
    }
    private void handleCities(String url, Token token, List<Country> countries) {
        List<CountryCityEntity> carriersLists = new CopyOnWriteArrayList<>();

        CountDownLatch countDownLatch = new CountDownLatch(countries.size());
        ExecutorService executor = Executors.newFixedThreadPool(10);

        for (Country country : countries) {
            executor.submit(new CitiesThread(
                    carriersLists, countDownLatch, country, token.getAccessToken(), url
            ));
        }
        try {
            countDownLatch.await();
            executor.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        writeAllCities(carriersLists);
    }
    private void writeAllCities(List<CountryCityEntity> countryCityEntities) {
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();

        for (CountryCityEntity countryCityEntity : countryCityEntities) {
            writeCityToHssFile(countryCityEntity.getCities(),
                    countryCityEntity.getCountry(), hssfWorkbook);
        }

        try {
            hssfWorkbook.write(new FileOutputStream(new File("cities.xls").getAbsolutePath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            hssfWorkbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void writeCityToHssFile(List<City> cities, Country country, HSSFWorkbook hssfWorkbook) {
        HSSFSheet sheet = null;
        try {
            sheet = hssfWorkbook.createSheet(country.getLocation());
        } catch (IllegalArgumentException e) {
            sheet = hssfWorkbook.getSheet(country.getLocation());

        }
        HSSFRow row;
        int last = sheet.getLastRowNum();
        for (int i = 0; i < cities.size(); i++) {
            row = sheet.createRow(last == 0 ? last + i : last + i + 1);
            row.createCell(0).setCellValue(cities.get(i).getCityCode());
            row.createCell(1).setCellValue(cities.get(i).getCity());
            row.createCell(2).setCellValue(cities.get(i).getStateCode());
            row.createCell(3).setCellValue(cities.get(i).getState());
            row.createCell(4).setCellValue(cities.get(i).getLocationCode());
        }

    }
    private void handleCarriers(String url, Token token, List<Country> countries) {
        List<CountryCarrierEntity> carriersLists = new CopyOnWriteArrayList<>();

        CountDownLatch countDownLatch = new CountDownLatch(countries.size());
        ExecutorService executor = Executors.newFixedThreadPool(10);
        for (Country country : countries) {
            executor.submit(new CarrierThread(
                    carriersLists, countDownLatch, country, token.getAccessToken(), url
            ));
        }
        try {
            countDownLatch.await();
            executor.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        writeAllCarriers(carriersLists);
    }
    private void writeAllCarriers(List<CountryCarrierEntity> countryCarrierEntities){
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();

        for (CountryCarrierEntity countryCarrierEntityEntity : countryCarrierEntities) {
            writeCarrierToHssFile(countryCarrierEntityEntity.getCarriers(),
                    countryCarrierEntityEntity.getCountry(), hssfWorkbook);
        }

        try {
            hssfWorkbook.write(new FileOutputStream(new File("carriers.xls").getAbsolutePath()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            hssfWorkbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void writeCarrierToHssFile(List<Carrier> carriers, Country country, HSSFWorkbook hssfWorkbook) {
        HSSFSheet sheet = null;
        try {
            sheet = hssfWorkbook.createSheet(country.getLocation());
        } catch (IllegalArgumentException e) {
            sheet = hssfWorkbook.getSheet(country.getLocation());

        }
        HSSFRow row;
        int last = sheet.getLastRowNum();
        for (int i = 0; i < carriers.size(); i++) {
            row = sheet.createRow(last == 0 ? last + i : last + i + 1);
            row.createCell(0).setCellValue(carriers.get(i).getValue());
        }

    }
    private void handleStates(String url, Token token, List<Country> countries) {
        List<CountryStateEntity> statesLists = new CopyOnWriteArrayList<>();

        CountDownLatch countDownLatch = new CountDownLatch(countries.size());
        ExecutorService executor = Executors.newFixedThreadPool(10);
        for (Country country : countries) {
            executor.submit(new StatesThread(
                    statesLists, countDownLatch, country, token.getAccessToken(), url
            ));
        }
        try {
            countDownLatch.await();
            executor.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        writeAllStates(statesLists);
    }

    private void writeAllStates(List<CountryStateEntity> states) {
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();

        for (CountryStateEntity countryStateEntity : states) {
            writeStateToXssFile(countryStateEntity.getStates(),
                    countryStateEntity.getCountry(), hssfWorkbook);
        }

        try {
            hssfWorkbook.write(new FileOutputStream(new File("states.xls").getAbsolutePath()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            hssfWorkbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void writeStateToXssFile(List<State> states, Country country, HSSFWorkbook hssfWorkbook) {

        HSSFSheet sheet = null;
        try {
            sheet = hssfWorkbook.createSheet(country.getLocation());
        } catch (IllegalArgumentException e) {
            sheet = hssfWorkbook.getSheet(country.getLocation());

        }
        HSSFRow row;
        int last = sheet.getLastRowNum();
        for (int i = 0; i < states.size(); i++) {
            row = sheet.createRow(last == 0 ? last + i : last + i + 1);
            row.createCell(0).setCellValue(states.get(i).getState());
            row.createCell(1).setCellValue(states.get(i).getStateCode());
            row.createCell(2).setCellValue(states.get(i).getLocationCode());
        }

    }

    private List<Country> getCountries(String url, HttpMethodsUtils methodsUtils, Map<String, String> headers) {
        String answer = methodsUtils.getMethod(url + "dict/location", headers);
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(List.class, new CountryDeserializer());
        Gson gson = builder.create();
        List<Country> countries = gson.fromJson(answer, List.class);
        return countries;
    }

    private Token getToken(String url, HttpMethodsUtils methodsUtils) {
        String answer = methodsUtils.getToken(url, "13256", "ae3a27715fb432f9ba036f163354e598");
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Token.class, new TokenDeserializer());
        Gson gson = builder.create();
        return gson.fromJson(answer, Token.class);
    }

}
