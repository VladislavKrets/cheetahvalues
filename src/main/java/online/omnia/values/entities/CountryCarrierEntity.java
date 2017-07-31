package online.omnia.values.entities;

import java.util.List;

/**
 * Created by lollipop on 31.07.2017.
 */
public class CountryCarrierEntity {
    private List<Carrier> carriers;
    private Country country;

    public CountryCarrierEntity(List<Carrier> carriers, Country country) {
        this.carriers = carriers;
        this.country = country;
    }

    public List<Carrier> getCarriers() {
        return carriers;
    }

    public Country getCountry() {
        return country;
    }
}
