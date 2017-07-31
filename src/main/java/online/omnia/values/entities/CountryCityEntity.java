package online.omnia.values.entities;

import java.util.List;

/**
 * Created by lollipop on 31.07.2017.
 */
public class CountryCityEntity {
    private List<City> cities;
    private Country country;

    public CountryCityEntity(List<City> cities, Country country) {
        this.cities = cities;
        this.country = country;
    }

    public List<City> getCities() {
        return cities;
    }

    public Country getCountry() {
        return country;
    }
}
