package online.omnia.values.entities;

/**
 * Created by lollipop on 31.07.2017.
 */
public class State {
    private String locationCode;
    private String stateCode;
    private String state;

    public State(String locationCode, String stateCode, String state) {
        this.locationCode = locationCode;
        this.stateCode = stateCode;
        this.state = state;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public String getStateCode() {
        return stateCode;
    }

    public String getState() {
        return state;
    }


    @Override
    public String toString() {
        return "State{" +
                "locationCode='" + locationCode + '\'' +
                ", stateCode='" + stateCode + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
