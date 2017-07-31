package online.omnia.values.entities;

/**
 * Created by lollipop on 31.07.2017.
 */
public class Country {
    private String locationCode;
    private String location;
    private String code;
    private String value;

    public Country(String locationCode, String location, String code, String value) {
        this.locationCode = locationCode;
        this.location = location;
        this.code = code;
        this.value = value;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public String getLocation() {
        return location;
    }

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Country{" +
                "locationCode='" + locationCode + '\'' +
                ", location='" + location + '\'' +
                ", code='" + code + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
