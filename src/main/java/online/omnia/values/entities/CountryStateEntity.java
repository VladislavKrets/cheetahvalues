package online.omnia.values.entities;

import java.util.List;

/**
 * Created by lollipop on 31.07.2017.
 */
public class CountryStateEntity {
    private List<State> states;
    private Country country;

    public CountryStateEntity(List<State> states, Country country) {
        this.states = states;
        this.country = country;
    }

    public List<State> getStates() {
        return states;
    }

    public Country getCountry() {
        return country;
    }
}
