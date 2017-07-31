package online.omnia.values.entities;

/**
 * Created by lollipop on 31.07.2017.
 */
public class Carrier {
    private String value;

    public Carrier(String locationCode) {
        this.value = locationCode;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Carrier{" +
                "locationCode='" + value + '\'' +
                '}';
    }
}
