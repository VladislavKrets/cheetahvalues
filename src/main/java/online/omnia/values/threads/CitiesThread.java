package online.omnia.values.threads;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import online.omnia.values.HttpMethodsUtils;
import online.omnia.values.deserializers.CityDecerializer;
import online.omnia.values.entities.*;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * Created by lollipop on 31.07.2017.
 */
public class CitiesThread implements Runnable{
    private CountDownLatch countDownLatch;
    private Country country;
    private String token;
    private String url;
    private HSSFWorkbook hssfWorkbook;

    public CitiesThread(CountDownLatch countDownLatch,
                        Country country, String token, String url, HSSFWorkbook hssfWorkbook) {
        this.countDownLatch = countDownLatch;
        this.country = country;
        this.token = token;
        this.url = url;
        this.hssfWorkbook = hssfWorkbook;
    }

    @Override
    public void run() {
        HttpMethodsUtils httpMethodsUtils = new HttpMethodsUtils();
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Accept", "application/json,application/x.orion.v1+json");
        headers.put("Authorization", "Bearer " + token);
        getCities(url, country.getLocationCode(), httpMethodsUtils, headers);
        countDownLatch.countDown();
    }
    private void getCities(String url, String locationCode,
                                 HttpMethodsUtils methodsUtils, Map<String, String> headers) {
        String answer = methodsUtils.getMethod(url + "dict/city?location_code=" + locationCode, headers);
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(List.class, new CityDecerializer());
        Gson gson = builder.create();
        List<City> cities = gson.fromJson(answer, List.class);
        writeCityToHssFile(cities, country, hssfWorkbook);
        cities = null;
    }
    private void writeCityToHssFile(List<City> cities, Country country, HSSFWorkbook hssfWorkbook) {
        synchronized (hssfWorkbook) {
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

    }
}
