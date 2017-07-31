package online.omnia.values.entities;

/**
 * Created by lollipop on 31.07.2017.
 */
public class City {
    private String cityCode;
    private String city;
    private String stateCode;
    private String state;
    private String locationCode;

    public City(String cityCode, String city, String stateCode, String state, String locationCode) {
        this.cityCode = cityCode;
        this.city = city;
        this.stateCode = stateCode;
        this.state = state;
        this.locationCode = locationCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public String getCity() {
        return city;
    }

    public String getStateCode() {
        return stateCode;
    }

    public String getState() {
        return state;
    }

    public String getLocationCode() {
        return locationCode;
    }
}
